package online.aquan.shortlink.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.esotericsoftware.minlog.Log;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import online.aquan.shortlink.project.common.constant.RedisKeyConstant;
import online.aquan.shortlink.project.common.convention.exception.ClientException;
import online.aquan.shortlink.project.common.convention.exception.ServiceException;
import online.aquan.shortlink.project.common.enums.VailDateTypeEnum;
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
import online.aquan.shortlink.project.toolkit.LinkUtil;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
@RequiredArgsConstructor
public class LinkServiceImpl extends ServiceImpl<LinkMapper, LinkDo> implements LinkService {

    private final RBloomFilter<String> shortLinkCachePenetrationBloomFilter;
    private final LinkGotoMapper linkGotoMapper;
    private final StringRedisTemplate stringRedisTemplate;
    private final RedissonClient redissonClient;

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
        linkGotoDo.setFullShortUrl(fullUrl);
        linkGotoMapper.insert(linkGotoDo);
        //缓存预热
        stringRedisTemplate.opsForValue().set(RedisKeyConstant.GOTO_LINK_KEY + fullUrl, requestParam.getOriginUrl(),
                LinkUtil.getValidCacheTime(requestParam.getValidDate()), TimeUnit.SECONDS);
        return LinkCreateRespDto.builder()
                .fullShortUrl("http://" + fullUrl)
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
        return linkDoIPage.convert((item) ->
        {
            LinkPageRespDto linkPageRespDto = BeanUtil.toBean(item, LinkPageRespDto.class);
            linkPageRespDto.setDomain("http://" + linkPageRespDto.getDomain());
            return linkPageRespDto;
        });
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

    @SneakyThrows
    @Override
    public void restoreLink(String shortUrl, ServletRequest servletRequest, ServletResponse servletResponse) {
        String fullUrl = servletRequest.getServerName() + "/" + shortUrl;
        String originUrl = stringRedisTemplate.opsForValue().get(RedisKeyConstant.GOTO_LINK_KEY + fullUrl);
        //如果缓存中直接就存在
        if (StrUtil.isNotBlank(originUrl)) {
            ((HttpServletResponse) servletResponse).sendRedirect(originUrl);
            return;
        }
        //缓存中不存在的话,我们就可以查询布隆过滤器看看fullUrl是否存在,不存在直接返回
        boolean contains = shortLinkCachePenetrationBloomFilter.contains(fullUrl);
        if (!contains) {
            return;
        }
        //布隆过滤器可能会误判,为了防止恶意误判的请求,使用判断空值来解决穿透问题
        String isNull = stringRedisTemplate.opsForValue().get(RedisKeyConstant.GOTO_LINK_IS_NULL_KEY + fullUrl);
        //如果这里不等于null,说明我们在数据库查询为空时已经在redis中标记了不存在
        if (StrUtil.isNotBlank(isNull)) {
            return;
        }
        RLock lock = redissonClient.getLock(RedisKeyConstant.LOCK_GOTO_LINK_KEY + fullUrl);
        lock.lock();
        try {
            //进来之后再次判断是否在缓存中,因为添加了分布式锁,在此之前可能已经存入缓存中
            String originUrl1 = stringRedisTemplate.opsForValue().get(RedisKeyConstant.GOTO_LINK_KEY + fullUrl);
            if (StrUtil.isNotBlank(originUrl1)) {
                ((HttpServletResponse) servletResponse).sendRedirect(originUrl);
                return;
            }
            LambdaQueryWrapper<LinkGotoDo> wrapper = Wrappers.lambdaQuery(LinkGotoDo.class)
                    .eq(LinkGotoDo::getFullShortUrl, fullUrl);
            LinkGotoDo linkGotoDo = linkGotoMapper.selectOne(wrapper);
            //如果数据库中也不存在,为了防止恶意的缓存穿透,设置一个短时间的值在redis中
            //这样的话在短时间内访问同样的导致布隆过滤器误判的短链接会被return
            if (linkGotoDo == null) {
                stringRedisTemplate.opsForValue().set(RedisKeyConstant.GOTO_LINK_IS_NULL_KEY + fullUrl, "-", 30, TimeUnit.SECONDS);
                return;
            }
            //根据shortUrl和gid查询出originUrl
            String gid = linkGotoDo.getGid();
            LambdaQueryWrapper<LinkDo> wrapper1 = Wrappers.lambdaQuery(LinkDo.class)
                    .eq(LinkDo::getGid, gid)
                    .eq(LinkDo::getShortUri, shortUrl)
                    .eq(LinkDo::getEnableStatus, 0);
            LinkDo linkDo = baseMapper.selectOne(wrapper1);
            if (linkDo != null) {
                ((HttpServletResponse) servletResponse).sendRedirect(linkDo.getOriginUrl());
                stringRedisTemplate.opsForValue().set(RedisKeyConstant.GOTO_LINK_KEY + fullUrl, linkDo.getOriginUrl());
            }
        } finally {
            lock.unlock();
        }
    }

}
