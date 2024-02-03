package online.aquan.shortlink.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import online.aquan.shortlink.project.common.constant.RedisKeyConstant;
import online.aquan.shortlink.project.dao.entity.LinkDo;
import online.aquan.shortlink.project.dao.mapper.LinkMapper;
import online.aquan.shortlink.project.dto.rep.RecycleBinCreateReqDto;
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
}
