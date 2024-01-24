package online.aquan.shortlink.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.esotericsoftware.minlog.Log;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.aquan.shortlink.project.common.convention.exception.ServiceException;
import online.aquan.shortlink.project.dao.entity.LinkDo;
import online.aquan.shortlink.project.dao.mapper.LinkMapper;
import online.aquan.shortlink.project.dto.rep.LinkCreateReqDto;
import online.aquan.shortlink.project.dto.resp.LinkCreateRespDto;
import online.aquan.shortlink.project.service.LinkService;
import online.aquan.shortlink.project.toolkit.HashUtil;
import org.redisson.api.RBloomFilter;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@Slf4j
@RequiredArgsConstructor
public class LinkServiceImpl extends ServiceImpl<LinkMapper, LinkDo> implements LinkService {

    private final RBloomFilter<String> shortLinkCachePenetrationBloomFilter;

    @Override
    public LinkCreateRespDto createShortLink(LinkCreateReqDto requestParam) {
        String shortUrl = generateShortUrl(requestParam.getOriginUrl());
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
                    .eq(LinkDo::getShortUri, shortUrl);
            LinkDo linkDo1 = baseMapper.selectOne(wrapper);
            if (linkDo1 != null) {
                Log.warn("短链接:{} 重复生成", shortUrl);
                throw new ServiceException("短链接重复生成");
            }
        }
        shortLinkCachePenetrationBloomFilter.add(shortUrl);
        return LinkCreateRespDto.builder()
                .fullShortUrl(fullUrl)
                .originUrl(requestParam.getOriginUrl())
                .gid(requestParam.getGid()).build();
    }

    private String generateShortUrl(String originUrl) {
        int count = 0;
        String shortUrl;
        while (true) {
            if (count >= 10) {
                throw new ServiceException("短链接创建过于频繁,请稍后再试");
            }
            String str = originUrl + LocalDateTime.now();
            shortUrl = HashUtil.hashToBase62(str);
            if (!shortLinkCachePenetrationBloomFilter.contains(shortUrl)) {
                break;
            }
            count++;
        }
        return shortUrl;
    }
}
