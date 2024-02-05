package online.aquan.shortlink.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Week;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.esotericsoftware.minlog.Log;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import online.aquan.shortlink.project.common.constant.RedisKeyConstant;
import online.aquan.shortlink.project.common.convention.exception.ClientException;
import online.aquan.shortlink.project.common.convention.exception.ServiceException;
import online.aquan.shortlink.project.common.enums.VailDateTypeEnum;
import online.aquan.shortlink.project.dao.entity.*;
import online.aquan.shortlink.project.dao.mapper.*;
import online.aquan.shortlink.project.dto.req.LinkCreateReqDto;
import online.aquan.shortlink.project.dto.req.LinkPageReqDto;
import online.aquan.shortlink.project.dto.req.LinkUpdateReqDto;
import online.aquan.shortlink.project.dto.resp.LinkCreateRespDto;
import online.aquan.shortlink.project.dto.resp.LinkGroupCountRespDto;
import online.aquan.shortlink.project.dto.resp.LinkPageRespDto;
import online.aquan.shortlink.project.service.LinkService;
import online.aquan.shortlink.project.toolkit.HashUtil;
import online.aquan.shortlink.project.toolkit.LinkUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static online.aquan.shortlink.project.common.constant.LinkConstant.amapApiUrl;


@Service
@Slf4j
@RequiredArgsConstructor
public class LinkServiceImpl extends ServiceImpl<LinkMapper, LinkDo> implements LinkService {

    private final RBloomFilter<String> shortLinkCachePenetrationBloomFilter;
    private final LinkGotoMapper linkGotoMapper;
    private final StringRedisTemplate stringRedisTemplate;
    private final RedissonClient redissonClient;
    private final LinkAccessStatsMapper linkAccessStatsMapper;
    private final LinkLocaleStatsMapper linkLocaleStatsMapper;
    private final LinkOsStatsMapper linkOsStatsMapper;
    private final LinkBrowserStatsMapper linkBrowserStatsMapper;
    private final LinkAccessLogsMapper linkAccessLogsMapper;

    @Value("${link.locale.stats.amap-key}")
    private String linkLocaleStatsAmapKey;

    /**
     * 创建短链接
     */
    @Override
    public LinkCreateRespDto createShortLink(LinkCreateReqDto requestParam) {
        String shortUrl = generateShortUrl(requestParam);
        String fullUrl = requestParam.getDomain() + "/" + shortUrl;
        String favicon = getFavicon(requestParam.getOriginUrl());
        LinkDo linkDo = LinkDo.builder()
                .domain(requestParam.getDomain())
                .originUrl(requestParam.getOriginUrl())
                .gid(requestParam.getGid())
                .favicon(favicon)
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
        String fullShortUrl = servletRequest.getServerName() + "/" + shortUrl;
        String originUrl = stringRedisTemplate.opsForValue().get(RedisKeyConstant.GOTO_LINK_KEY + fullShortUrl);
        //如果缓存中直接就存在
        if (StrUtil.isNotBlank(originUrl)) {
            linkStats(fullShortUrl, null, servletRequest, servletResponse);
            ((HttpServletResponse) servletResponse).sendRedirect(originUrl);
            return;
        }
        //缓存中不存在的话,我们就可以查询布隆过滤器看看fullUrl是否存在,不存在直接返回
        boolean contains = shortLinkCachePenetrationBloomFilter.contains(fullShortUrl);
        if (!contains) {
            ((HttpServletResponse) servletResponse).sendRedirect("/page/notfound");
            return;
        }
        //布隆过滤器可能会误判导致不存在的变成存在,为了防止恶意误判的请求,使用判断空值来解决穿透问题
        String isNull = stringRedisTemplate.opsForValue().get(RedisKeyConstant.GOTO_LINK_IS_NULL_KEY + fullShortUrl);
        //如果这里不等于null,说明我们在数据库查询为空时已经在redis中标记了不存在
        if (StrUtil.isNotBlank(isNull)) {
            ((HttpServletResponse) servletResponse).sendRedirect("/page/notfound");
            return;
        }
        RLock lock = redissonClient.getLock(RedisKeyConstant.LOCK_GOTO_LINK_KEY + fullShortUrl);
        lock.lock();
        try {
            //进来之后再次判断是否在缓存中,因为添加了分布式锁,在此之前可能已经存入缓存中
            String originUrl1 = stringRedisTemplate.opsForValue().get(RedisKeyConstant.GOTO_LINK_KEY + fullShortUrl);
            if (StrUtil.isNotBlank(originUrl1)) {
                linkStats(fullShortUrl, null, servletRequest, servletResponse);
                ((HttpServletResponse) servletResponse).sendRedirect(originUrl);
                return;
            }
            //根据fullShortUrl找出对应的gid
            LambdaQueryWrapper<LinkGotoDo> wrapper = Wrappers.lambdaQuery(LinkGotoDo.class)
                    .eq(LinkGotoDo::getFullShortUrl, fullShortUrl);
            LinkGotoDo linkGotoDo = linkGotoMapper.selectOne(wrapper);
            //如果数据库中也不存在,为了防止恶意的缓存穿透,设置一个短时间的值在redis中
            //这样的话在短时间内访问同样的导致布隆过滤器误判的短链接会被return
            if (linkGotoDo == null) {
                stringRedisTemplate.opsForValue().set(RedisKeyConstant.GOTO_LINK_IS_NULL_KEY + fullShortUrl, "-", 30, TimeUnit.MINUTES);
                ((HttpServletResponse) servletResponse).sendRedirect("/page/notfound");
                return;
            }
            //根据shortUrl和gid查询出originUrl
            String gid = linkGotoDo.getGid();
            LambdaQueryWrapper<LinkDo> wrapper1 = Wrappers.lambdaQuery(LinkDo.class)
                    .eq(LinkDo::getGid, gid)
                    .eq(LinkDo::getShortUri, shortUrl)
                    .eq(LinkDo::getEnableStatus, 0);
            LinkDo linkDo = baseMapper.selectOne(wrapper1);
            //如果为null或者不为null但是已经过期了
            if (linkDo == null || (linkDo.getValidDate() != null && linkDo.getValidDate().before(new Date()))) {
                stringRedisTemplate.opsForValue().set(RedisKeyConstant.GOTO_LINK_IS_NULL_KEY + fullShortUrl, "-", 30, TimeUnit.MINUTES);
                ((HttpServletResponse) servletResponse).sendRedirect("/page/notfound");
                return;
            }
            //设置缓存有效期
            stringRedisTemplate.opsForValue().set(RedisKeyConstant.GOTO_LINK_KEY + fullShortUrl, linkDo.getOriginUrl(),
                    LinkUtil.getValidCacheTime(linkDo.getValidDate()), TimeUnit.SECONDS);
            linkStats(fullShortUrl, linkGotoDo.getGid(), servletRequest, servletResponse);
            ((HttpServletResponse) servletResponse).sendRedirect(linkDo.getOriginUrl());
        } finally {
            lock.unlock();
        }
    }

    private void linkStats(String fullShortUrl, String gid, ServletRequest servletRequest, ServletResponse servletResponse) {
        AtomicBoolean uvIsFirst = new AtomicBoolean();
        //通过cookies判断是否是同一个用户访问
        Cookie[] cookies = ((HttpServletRequest) servletRequest).getCookies();

        //如果使用的缓存找到的originUrl,那么就没有gid
        try {
            AtomicReference<String > uv = new AtomicReference<>();
            Runnable addCookiesAction = () -> {
                //生成一个cookie并且加入到redis里面
                uv.set(UUID.fastUUID().toString());
                Cookie uvCookie = new Cookie("uv", uv.get());
                uvCookie.setMaxAge(60 * 60 * 24 * 30);
                //限制cookie的路径
                uvCookie.setPath(StrUtil.sub(fullShortUrl, fullShortUrl.indexOf("/"), fullShortUrl.length()));
                ((HttpServletResponse) servletResponse).addCookie(uvCookie);
                //标记第一次访问并存入redis
                uvIsFirst.set(Boolean.TRUE);
                stringRedisTemplate.opsForSet().add("short-link:stats:uv" + fullShortUrl, uv.get());
            };

            if (ArrayUtil.isNotEmpty(cookies)) {
                //如果能在请求中找到cookie
                Arrays.stream(cookies)
                        .filter(each -> Objects.equals(each.getName(), "uv"))
                        .findFirst()
                        .map(Cookie::getValue)
                        .ifPresentOrElse(
                                //找得到uv的key还要判断是否是合法的key
                                Each -> {
                                    uv.set(Each);
                                    //add方法返回的是实际添加进入集合中的数量
                                    Long uvAdded = stringRedisTemplate.opsForSet().add("short-link:stats:uv" + fullShortUrl, Each);
                                    uvIsFirst.set(uvAdded != null && uvAdded > 0L);
                                },
                                //如果请求中的cookie里面找不到uv这个key就执行添加
                                addCookiesAction);
            } else {
                //不能就添加
                addCookiesAction.run();
            }

            //获取请求的ip
            String uip = LinkUtil.getActualIp((HttpServletRequest) servletRequest);
            Long uipAdded = stringRedisTemplate.opsForSet().add("short-link:stats:uip" + fullShortUrl, uip);
            boolean uipIsFirst = uipAdded != null && uipAdded > 0L;

            if (StrUtil.isEmpty(gid)) {
                LambdaQueryWrapper<LinkGotoDo> wrapper = Wrappers.lambdaQuery(LinkGotoDo.class)
                        .eq(LinkGotoDo::getFullShortUrl, fullShortUrl);
                LinkGotoDo linkGotoDo = linkGotoMapper.selectOne(wrapper);
                gid = linkGotoDo.getGid();
            }

            Date date = new Date();
            int hour = DateUtil.hour(date, true);
            Week week = DateUtil.dayOfWeekEnum(date);
            int weekday = week.getIso8601Value();
            LinkAccessStatsDo linkAccessStatsDo = LinkAccessStatsDo.builder()
                    .pv(1)
                    .uv(uvIsFirst.get() ? 1 : 0)
                    .uip(uipIsFirst ? 1 : 0)
                    .weekday(weekday)
                    .date(date)
                    .fullShortUrl(fullShortUrl)
                    .gid(gid)
                    .hour(hour)
                    .build();
            linkAccessStatsDo.setCreateTime(date);
            linkAccessStatsDo.setUpdateTime(date);
            linkAccessStatsMapper.insertOrUpdate(linkAccessStatsDo);

            //统计地区
            Map<String, Object> map = new HashMap<>();
            map.put("key", linkLocaleStatsAmapKey);
            map.put("ip", uip);
            String resp = HttpUtil.get(amapApiUrl, map);
            JSONObject locale = JSON.parseObject(resp);

            String infoCode = locale.getString("infocode");
            if (StrUtil.isNotBlank(infoCode) && Objects.equals(infoCode, "10000")) {
                String province = locale.getString("province");
                boolean isNull = Objects.equals(province, "[]");
                LinkLocaleStatsDo localeStatsDo = LinkLocaleStatsDo.builder()
                        .country(isNull ? "未知" : locale.getString("country"))
                        .gid(gid)
                        .cnt(1)
                        .fullShortUrl(fullShortUrl)
                        .city(isNull ? "未知" : locale.getString("city"))
                        .adcode(isNull ? "未知" : locale.getString("adcode"))
                        .province(isNull ? "未知" : locale.getString("province"))
                        .date(date)
                        .build();
                linkLocaleStatsMapper.insertOrUpdate(localeStatsDo);
            }
            //统计操作系统
            String os = LinkUtil.getOs((HttpServletRequest) servletRequest);
            LinkOsStatsDo linkOsStatsDo = LinkOsStatsDo.builder()
                    .fullShortUrl(fullShortUrl)
                    .gid(gid)
                    .cnt(1)
                    .os(os)
                    .date(date).build();
            linkOsStatsDo.setCreateTime(date);
            linkOsStatsDo.setUpdateTime(date);
            linkOsStatsMapper.insertOrUpdate(linkOsStatsDo);
            //统计浏览器
            String browser = LinkUtil.getBrowser((HttpServletRequest) servletRequest);
            LinkBrowserStatsDo linkBrowserStatsDo = LinkBrowserStatsDo.builder()
                    .fullShortUrl(fullShortUrl)
                    .gid(gid)
                    .cnt(1)
                    .browser(browser)
                    .date(date).build();
            linkBrowserStatsDo.setCreateTime(date);
            linkBrowserStatsDo.setUpdateTime(date);
            linkBrowserStatsMapper.insertOrUpdate(linkBrowserStatsDo);
            //统计高频访问ip
            LinkAccessLogsDo linkAccessLogsDo = LinkAccessLogsDo.builder()
                    .ip(uip)
                    .browser(browser)
                    .os(os)
                    .fullShortUrl(fullShortUrl)
                    .gid(gid)
                    .user(uv.get())
                    .build();
            linkAccessLogsDo.setCreateTime(date);
            linkAccessLogsDo.setUpdateTime(date);
            linkAccessLogsMapper.insert(linkAccessLogsDo);
            
        } catch (Exception e) {
            log.error("短链接访问量统计异常", e);
        }
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

    @SneakyThrows
    private String getFavicon(String originUrl) {
        URL targetUrl = new URL(originUrl);
        HttpURLConnection connection = (HttpURLConnection) targetUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int responseCode = connection.getResponseCode();
        if (HttpURLConnection.HTTP_OK == responseCode) {
            Document document = Jsoup.connect(originUrl).get();
            Element faviconLink = document.select("link[rel~=(?i)^(shortcut )?icon]").first();
            if (faviconLink != null) {
                return faviconLink.attr("abs:href");
            }
        }
        return null;
    }

}
