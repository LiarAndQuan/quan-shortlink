package online.aquan.shortlink.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.esotericsoftware.minlog.Log;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.aquan.shortlink.project.common.constant.VailDateTypeEnum;
import online.aquan.shortlink.project.common.convention.exception.ClientException;
import online.aquan.shortlink.project.common.convention.exception.ServiceException;
import online.aquan.shortlink.project.dao.entity.LinkDo;
import online.aquan.shortlink.project.dao.entity.LinkGotoDo;
import online.aquan.shortlink.project.dao.mapper.LinkGotoMapper;
import online.aquan.shortlink.project.dao.mapper.LinkMapper;
import online.aquan.shortlink.project.dto.rep.LinkCreateReqDto;
import online.aquan.shortlink.project.dto.rep.LinkPageReqDto;
import online.aquan.shortlink.project.dto.rep.LinkUpdateReqDto;
import online.aquan.shortlink.project.dto.resp.LinkCreateRespDto;
import online.aquan.shortlink.project.dto.resp.LinkGroupCountRespDto;
import online.aquan.shortlink.project.dto.resp.LinkPageRespDto;
import online.aquan.shortlink.project.service.LinkService;
import online.aquan.shortlink.project.toolkit.HashUtil;
import org.redisson.api.RBloomFilter;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Service
@Slf4j
@RequiredArgsConstructor
public class LinkServiceImpl extends ServiceImpl<LinkMapper, LinkDo> implements LinkService {

    private final RBloomFilter<String> shortLinkCachePenetrationBloomFilter;
    private final LinkGotoMapper linkGotoMapper;

    /**
     * 创建短链接
     */
    @Override
    public LinkCreateRespDto createShortLink(LinkCreateReqDto requestParam) {
        String shortUrl = generateShortUrl(requestParam);
        String fullUrl = requestParam.getDomain() + "/" + shortUrl;
        LinkDo linkDo = LinkDo.builder()
                .domain(requestParam.getDomain())
                .originUrl(requestParam.getOriginUrl())
                .gid(requestParam.getGid())
                .createdType(requestParam.getCreatedType())
                .validDateType(requestParam.getValidDateType())
                .validDate(requestParam.getValidDate())
                .describe(requestParam.getDescribe())
                .enableStatus(0)
                .shortUri(shortUrl)
                .fullShortUrl(fullUrl).build();
        try {
            baseMapper.insert(linkDo);
        } catch (DuplicateKeyException e) {
            /*如果加入布隆过滤器之后还是出现了插入重复key的情况
            那么就是同时有很多个相同的请求一起发送过来导致的并发问题
            此时查询数据库*/
            LambdaQueryWrapper<LinkDo> wrapper = Wrappers.lambdaQuery(LinkDo.class)
                    .eq(LinkDo::getFullShortUrl, fullUrl);
            LinkDo linkDo1 = baseMapper.selectOne(wrapper);
            if (linkDo1 != null) {
                Log.warn("短链接:{} 重复生成", fullUrl);
                throw new ServiceException("短链接重复生成");
            }
        }
        shortLinkCachePenetrationBloomFilter.add(fullUrl);
        //插入之后还需要新增一条linkGoto记录
        LinkGotoDo linkGotoDo = new LinkGotoDo();
        linkGotoDo.setGid(requestParam.getGid());
        linkGotoDo.setShortUrl(fullUrl);
        linkGotoMapper.insert(linkGotoDo);
        return LinkCreateRespDto.builder()
                .fullShortUrl(fullUrl)
                .originUrl(requestParam.getOriginUrl())
                .gid(requestParam.getGid()).build();
    }

    /**
     * 生成短链接的shortUrl
     */
    private String generateShortUrl(LinkCreateReqDto requestParam) {
        int count = 0;
        String shortUrl;
        while (true) {
            if (count >= 10) {
                throw new ServiceException("短链接创建过于频繁,请稍后再试");
            }
            String str = requestParam.getOriginUrl() + LocalDateTime.now();
            shortUrl = HashUtil.hashToBase62(str);
            if (!shortLinkCachePenetrationBloomFilter.contains(requestParam.getDomain() + "/" + shortUrl)) {
                break;
            }
            count++;
        }
        return shortUrl;
    }

    /**
     * 短链接分页查询
     *
     * @param requestParam current size ,LinkPageReqDto继承了IPage
     */
    @Override
    public IPage<LinkPageRespDto> pageShortLink(LinkPageReqDto requestParam) {
        LambdaQueryWrapper<LinkDo> wrapper = Wrappers.lambdaQuery(LinkDo.class)
                .eq(LinkDo::getGid, requestParam.getGid())
                .eq(LinkDo::getEnableStatus, 0)
                .orderByDesc(LinkDo::getCreateTime);
        //分页查询即可
        IPage<LinkDo> linkDoIPage = baseMapper.selectPage(requestParam, wrapper);
        return linkDoIPage.convert((item) -> BeanUtil.toBean(item, LinkPageRespDto.class));
    }

    /**
     * 短链接分组中有多少条短链接
     *
     * @param requestParam List --gid
     * @return gid, linkCount
     */
    @Override
    public List<LinkGroupCountRespDto> getLinkGroupCount(List<String> requestParam) {
        QueryWrapper<LinkDo> wrapper = Wrappers.query(new LinkDo())
                .select("gid ,count(*) as shortLinkCount")
                .groupBy("gid")
                .in("gid", requestParam)
                .eq("enable_status", 0);
        List<Map<String, Object>> maps = baseMapper.selectMaps(wrapper);
        return BeanUtil.copyToList(maps, LinkGroupCountRespDto.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateLink(LinkUpdateReqDto requestParam) {
        LambdaQueryWrapper<LinkDo> wrapper = Wrappers.lambdaQuery(LinkDo.class)
                .eq(LinkDo::getGid, requestParam.getGid())
                .eq(LinkDo::getFullShortUrl, requestParam.getFullShortUrl())
                .eq(LinkDo::getEnableStatus, 0);
        LinkDo originLinkDo = baseMapper.selectOne(wrapper);
        if (originLinkDo == null) {
            throw new ClientException("修改的短链接不存在");
        }
        //如果是一样的gid,那么就直接修改就可以了,否则就需要先删除再插入,因为使用的是gid分片
        LinkDo linkDo = LinkDo.builder()
                .domain(originLinkDo.getDomain())
                .shortUri(originLinkDo.getShortUri())
                .fullShortUrl(originLinkDo.getFullShortUrl())
                .clickNum(originLinkDo.getClickNum())
                .gid(originLinkDo.getGid())
                .createdType(originLinkDo.getCreatedType())
                .originUrl(requestParam.getOriginUrl())
                .favicon(requestParam.getFavicon())
                .enableStatus(0)
                .validDateType(requestParam.getValidDateType())
                .validDate(requestParam.getValidDateType() == VailDateTypeEnum.PERMANENT.getType() ?
                        null : requestParam.getValidDate())
                .describe(requestParam.getDescribe())
                .build();
        if (Objects.equals(requestParam.getGid(), originLinkDo.getGid())) {
            LambdaUpdateWrapper<LinkDo> updateWrapper = Wrappers.lambdaUpdate(LinkDo.class)
                    .eq(LinkDo::getFullShortUrl, requestParam.getFullShortUrl())
                    .eq(LinkDo::getGid, requestParam.getGid())
                    .eq(LinkDo::getEnableStatus, 0);
            baseMapper.update(linkDo, updateWrapper);
        } else {
            LambdaQueryWrapper<LinkDo> deleteWrapper = Wrappers.lambdaQuery(LinkDo.class)
                    .eq(LinkDo::getFullShortUrl, requestParam.getFullShortUrl())
                    .eq(LinkDo::getGid, requestParam.getGid())
                    .eq(LinkDo::getEnableStatus, 0);
            baseMapper.delete(deleteWrapper);
            //todo 这里需要将LinkDo里面的gid改成requestParam里面传入的新gid
            baseMapper.insert(linkDo);
        }
    }
    @Override
    public void restoreLink(String shortUrl, ServletRequest servletRequest, ServletResponse servletResponse) {
//        String fullUrl = servletRequest.getServerName() + "/" + shortUrl;
//        //首先查出短链接对应的gid
//        LambdaQueryWrapper<LinkGotoDo> wrapper = Wrappers.lambdaQuery(LinkGotoDo.class)
//                .eq(LinkGotoDo::getShortUrl, fullUrl);
//        LinkGotoDo linkGotoDo = linkGotoMapper.selectOne(wrapper);
//        if (linkGotoDo == null) {
//            return;
//        }
//        String gid = linkGotoDo.getGid();

    }


}
