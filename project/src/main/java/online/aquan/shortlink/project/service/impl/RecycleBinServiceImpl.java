package online.aquan.shortlink.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import online.aquan.shortlink.project.common.constant.RedisKeyConstant;
import online.aquan.shortlink.project.dao.entity.LinkDo;
import online.aquan.shortlink.project.dao.mapper.LinkMapper;
import online.aquan.shortlink.project.dto.req.RecycleBinCreateReqDto;
import online.aquan.shortlink.project.dto.req.RecycleBinPageReqDto;
import online.aquan.shortlink.project.dto.req.RecycleBinRecoverReqDto;
import online.aquan.shortlink.project.dto.resp.LinkPageRespDto;
import online.aquan.shortlink.project.service.RecycleBinService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecycleBinServiceImpl extends ServiceImpl<LinkMapper, LinkDo> implements RecycleBinService {

    private final StringRedisTemplate stringRedisTemplate;


    @Override
    public void saveRecycleBin(RecycleBinCreateReqDto requestParam) {
        LambdaQueryWrapper<LinkDo> wrapper = Wrappers.lambdaQuery(LinkDo.class)
                .eq(LinkDo::getEnableStatus, 0)
                .eq(LinkDo::getGid, requestParam.getGid())
                .eq(LinkDo::getFullShortUrl, requestParam.getFullShortUrl());
        LinkDo linkDo = LinkDo.builder()
                .enableStatus(1).build();
        baseMapper.update(linkDo, wrapper);
        stringRedisTemplate.delete(RedisKeyConstant.GOTO_LINK_KEY + requestParam.getFullShortUrl());
    }

    /**
     * 短链接分页查询
     *
     * @param requestParam current size ,LinkPageReqDto继承了IPage
     */
    @Override
    public IPage<LinkPageRespDto> pageRecycleBinShortLink(RecycleBinPageReqDto requestParam) {
        LambdaQueryWrapper<LinkDo> wrapper = Wrappers.lambdaQuery(LinkDo.class)
                .in(LinkDo::getGid, requestParam.getGidList())
                .eq(LinkDo::getEnableStatus, 0)
                .orderByDesc(LinkDo::getUpdateTime);
        //分页查询即可
        IPage<LinkDo> linkDoIPage = baseMapper.selectPage(requestParam, wrapper);
        return linkDoIPage.convert((item) ->
        {
            LinkPageRespDto linkPageRespDto = BeanUtil.toBean(item, LinkPageRespDto.class);
            linkPageRespDto.setDomain("http://" + linkPageRespDto.getDomain());
            return linkPageRespDto;
        });
    }

    @Override
    public void recoverRecycleBinLink(RecycleBinRecoverReqDto requestParam) {
        LambdaUpdateWrapper<LinkDo> wrapper = Wrappers.lambdaUpdate(LinkDo.class)
                .eq(LinkDo::getEnableStatus, 1)
                .eq(LinkDo::getGid, requestParam.getGid())
                .eq(LinkDo::getFullShortUrl, requestParam.getFullShortUrl());
        LinkDo linkDo = LinkDo.builder().enableStatus(0).build();
        baseMapper.update(linkDo, wrapper);
        stringRedisTemplate.delete(RedisKeyConstant.GOTO_LINK_IS_NULL_KEY + requestParam.getFullShortUrl());
    }
    
}
