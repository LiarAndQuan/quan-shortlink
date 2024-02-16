package online.aquan.shortlink.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
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
import online.aquan.shortlink.project.config.GotoDomainWhiteListConfiguration;
import online.aquan.shortlink.project.dao.entity.*;
import online.aquan.shortlink.project.dao.mapper.*;
import online.aquan.shortlink.project.dto.biz.LinkStatsRecordDto;
import online.aquan.shortlink.project.dto.req.LinkBatchCreateReqDto;
import online.aquan.shortlink.project.dto.req.LinkCreateReqDto;
import online.aquan.shortlink.project.dto.req.LinkPageReqDto;
import online.aquan.shortlink.project.dto.req.LinkUpdateReqDto;
import online.aquan.shortlink.project.dto.resp.*;
import online.aquan.shortlink.project.mq.producer.DelayLinkStatsProducer;
import online.aquan.shortlink.project.service.LinkService;
import online.aquan.shortlink.project.service.LinkStatsTodayService;
import online.aquan.shortlink.project.toolkit.HashUtil;
import online.aquan.shortlink.project.toolkit.LinkUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.redisson.api.*;
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
    private final LinkDeviceStatsMapper linkDeviceStatsMapper;
    private final LinkNetworkStatsMapper linkNetworkStatsMapper;
    private final LinkStatsTodayMapper linkStatsTodayMapper;
    private final LinkStatsTodayService linkStatsTodayService;
    private final DelayLinkStatsProducer delayLinkStatsProducer;
    private final GotoDomainWhiteListConfiguration gotoDomainWhiteListConfiguration;

    @Value("${link.locale.stats.amap-key}")
    private String linkLocaleStatsAmapKey;

    @Value("${link.domain.default}")
    private String defaultDomain;

    /**
     * 创建短链接
     */
    @Override
    public LinkCreateRespDto createShortLink(LinkCreateReqDto requestParam) {
        verificationWhitelist(requestParam.getOriginUrl());
        String shortUrl = generateShortUrl(requestParam);
        String fullUrl = defaultDomain + "/" + shortUrl;
        String favicon = getFavicon(requestParam.getOriginUrl());
        LinkDo linkDo = LinkDo.builder()
                .domain(defaultDomain)
                .originUrl(requestParam.getOriginUrl())
                .gid(requestParam.getGid())
                .favicon(favicon)
                .createdType(requestParam.getCreatedType())
                .validDateType(requestParam.getValidDateType())
                .validDate(requestParam.getValidDate())
                .describe(requestParam.getDescribe())
                .enableStatus(0)
                .totalPv(0)
                .totalUip(0)
                .totalUv(0)
                .delTime(0L)
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

    @Override
    public LinkBatchCreateRespDto batchCreateShortLink(LinkBatchCreateReqDto requestParam) {
        List<String> originUrls = requestParam.getOriginUrls();
        List<String> describes = requestParam.getDescribes();
        List<LinkBaseInfoRespDto> baseInfoRespDtoArrayList = new ArrayList<>();
        for (int i = 0; i < originUrls.size(); i++) {
            LinkCreateReqDto linkCreateReqDto = LinkCreateReqDto.builder()
                    .originUrl(originUrls.get(i))
                    .describe(describes.get(i))
                    .gid(requestParam.getGid())
                    .validDate(requestParam.getValidDate())
                    .validDateType(requestParam.getValidDateType())
                    .createdType(requestParam.getCreatedType())
                    .build();
            try {
                LinkCreateRespDto shortLink = createShortLink(linkCreateReqDto);
                LinkBaseInfoRespDto linkBaseInfoRespDto = LinkBaseInfoRespDto.builder()
                        .fullShortUrl(shortLink.getFullShortUrl())
                        .originUrl(shortLink.getOriginUrl())
                        .describe(describes.get(i))
                        .build();
                baseInfoRespDtoArrayList.add(linkBaseInfoRespDto);
            } catch (Exception e) {
                log.info("批量创建短链接失败,原始参数{}", originUrls.get(i));
            }
        }
        return LinkBatchCreateRespDto.builder()
                .baseLinkInfos(baseInfoRespDtoArrayList)
                .total(baseInfoRespDtoArrayList.size())
                .build();
    }

    /**
     * 短链接分页查询
     *
     * @param requestParam current size ,LinkPageReqDto继承了IPage
     */
    @Override
    public IPage<LinkPageRespDto> pageShortLink(LinkPageReqDto requestParam) {
        //分页查询即可
        IPage<LinkDo> linkDoIPage = baseMapper.pageLink(requestParam);
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
                .in("gid", requestParam)
                .eq("del_time", 0L)
                .eq("enable_status", 0)
                .groupBy("gid");
        List<Map<String, Object>> maps = baseMapper.selectMaps(wrapper);
        return BeanUtil.copyToList(maps, LinkGroupCountRespDto.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateLink(LinkUpdateReqDto requestParam) {
        LambdaQueryWrapper<LinkDo> wrapper = Wrappers.lambdaQuery(LinkDo.class)
                .eq(LinkDo::getGid, requestParam.getOriginGid())
                .eq(LinkDo::getFullShortUrl, requestParam.getFullShortUrl())
                .eq(LinkDo::getEnableStatus, 0);
        LinkDo originLinkDo = baseMapper.selectOne(wrapper);
        if (originLinkDo == null) {
            throw new ClientException("修改的短链接不存在");
        }

        //如果传入的gid是相同的,那么直接修改就好了
        if (Objects.equals(requestParam.getOriginGid(), requestParam.getGid())) {
            LambdaUpdateWrapper<LinkDo> updateWrapper = Wrappers.lambdaUpdate(LinkDo.class)
                    .eq(LinkDo::getFullShortUrl, requestParam.getFullShortUrl())
                    .eq(LinkDo::getGid, requestParam.getGid())
                    .eq(LinkDo::getEnableStatus, 0)
                    .set(Objects.equals(requestParam.getValidDateType(), VailDateTypeEnum.PERMANENT.getType()), LinkDo::getValidDate, null);
            LinkDo linkDo = LinkDo.builder()
                    .domain(originLinkDo.getDomain())
                    .shortUri(originLinkDo.getShortUri())
                    .fullShortUrl(originLinkDo.getFullShortUrl())
                    .clickNum(originLinkDo.getClickNum())
                    .favicon(originLinkDo.getFavicon())
                    .createdType(originLinkDo.getCreatedType())
                    .gid(requestParam.getGid())
                    .originUrl(requestParam.getOriginUrl())
                    .enableStatus(0)
                    .validDateType(requestParam.getValidDateType())
                    .validDate(requestParam.getValidDate())
                    .describe(requestParam.getDescribe())
                    .build();
            baseMapper.update(linkDo, updateWrapper);
        } else {
           /* 如果传入的gid不相同,那么需要先删除再插入
            如果访问的时候正好修改了短链接的信息,那么监控日志之类的就可能会记录出错,所以需要引入锁
            又由于分布式锁不适合这种场景,使用读写锁更合适*/

            //获取写锁
            RReadWriteLock readWriteLock = redissonClient.getReadWriteLock(RedisKeyConstant.LOCK_GID_UPDATE_KEY + requestParam.getFullShortUrl());
            RLock rLock = readWriteLock.writeLock();
            //如果获取不到说明有人在访问,返回提示
            if (!rLock.tryLock()) {
                throw new ClientException("短链接正在被访问,请稍后再试");
            }
            try {
                //首先删除原有的数据,这里的删除是将delTime置为当前时间戳
                LambdaQueryWrapper<LinkDo> deleteWrapper = Wrappers.lambdaQuery(LinkDo.class)
                        .eq(LinkDo::getFullShortUrl, requestParam.getFullShortUrl())
                        .eq(LinkDo::getGid, requestParam.getOriginGid())
                        .eq(LinkDo::getDelTime, 0L)
                        .eq(LinkDo::getEnableStatus, 0);
                LinkDo delLinkDo = LinkDo.builder()
                        .delTime(System.currentTimeMillis())
                        .build();
                delLinkDo.setDelFlag(1);
                baseMapper.update(delLinkDo, deleteWrapper);
                //添加上新的Link数据
                LinkDo newLinkDo = LinkDo.builder()
                        .domain(defaultDomain)
                        .originUrl(requestParam.getOriginUrl())
                        .gid(requestParam.getGid())
                        .createdType(originLinkDo.getCreatedType())
                        .validDateType(requestParam.getValidDateType())
                        .validDate(requestParam.getValidDate())
                        .describe(requestParam.getDescribe())
                        .shortUri(originLinkDo.getShortUri())
                        .enableStatus(originLinkDo.getEnableStatus())
                        .totalPv(originLinkDo.getTotalPv())
                        .totalUv(originLinkDo.getTotalUv())
                        .totalUip(originLinkDo.getTotalUip())
                        .fullShortUrl(originLinkDo.getFullShortUrl())
                        .favicon(getFavicon(requestParam.getOriginUrl()))
                        .delTime(0L)
                        .build();
                baseMapper.insert(newLinkDo);
                //删除原先gid今日监控表中的数据,再添加进新的
                LambdaQueryWrapper<LinkStatsTodayDo> statsTodayDoLambdaQueryWrapper = Wrappers.lambdaQuery(LinkStatsTodayDo.class)
                        .eq(LinkStatsTodayDo::getFullShortUrl, requestParam.getFullShortUrl())
                        .eq(LinkStatsTodayDo::getGid, requestParam.getOriginGid());
                List<LinkStatsTodayDo> linkStatsTodayDos = linkStatsTodayMapper.selectList(statsTodayDoLambdaQueryWrapper);
                if (CollUtil.isNotEmpty(linkStatsTodayDos)) {
                    linkStatsTodayMapper.deleteBatchIds(linkStatsTodayDos.stream().map(LinkStatsTodayDo::getId).toList());
                    linkStatsTodayDos.forEach(each -> each.setGid(requestParam.getGid()));
                    linkStatsTodayService.saveBatch(linkStatsTodayDos);
                }
                //删除原先gid goto表中的数据,再添加进新的
                LambdaQueryWrapper<LinkGotoDo> linkGotoDoLambdaQueryWrapper = Wrappers.lambdaQuery(LinkGotoDo.class)
                        .eq(LinkGotoDo::getGid, originLinkDo.getGid())
                        .eq(LinkGotoDo::getFullShortUrl, requestParam.getFullShortUrl());
                LinkGotoDo linkGotoDo = linkGotoMapper.selectOne(linkGotoDoLambdaQueryWrapper);
                linkGotoMapper.deleteById(linkGotoDo.getId());
                linkGotoDo.setGid(requestParam.getGid());
                linkGotoMapper.insert(linkGotoDo);
                //下面是各个监控表的修改
                LambdaUpdateWrapper<LinkAccessStatsDo> linkAccessStatsUpdateWrapper = Wrappers.lambdaUpdate(LinkAccessStatsDo.class)
                        .eq(LinkAccessStatsDo::getFullShortUrl, requestParam.getFullShortUrl())
                        .eq(LinkAccessStatsDo::getGid, originLinkDo.getGid())
                        .eq(LinkAccessStatsDo::getDelFlag, 0);
                LinkAccessStatsDo linkAccessStatsDo = LinkAccessStatsDo.builder()
                        .gid(requestParam.getGid())
                        .build();
                linkAccessStatsMapper.update(linkAccessStatsDo, linkAccessStatsUpdateWrapper);
                LambdaUpdateWrapper<LinkLocaleStatsDo> linkLocaleStatsUpdateWrapper = Wrappers.lambdaUpdate(LinkLocaleStatsDo.class)
                        .eq(LinkLocaleStatsDo::getFullShortUrl, requestParam.getFullShortUrl())
                        .eq(LinkLocaleStatsDo::getGid, originLinkDo.getGid())
                        .eq(LinkLocaleStatsDo::getDelFlag, 0);
                LinkLocaleStatsDo linkLocaleStatsDo = LinkLocaleStatsDo.builder()
                        .gid(requestParam.getGid())
                        .build();
                linkLocaleStatsMapper.update(linkLocaleStatsDo, linkLocaleStatsUpdateWrapper);
                LambdaUpdateWrapper<LinkOsStatsDo> linkOsStatsUpdateWrapper = Wrappers.lambdaUpdate(LinkOsStatsDo.class)
                        .eq(LinkOsStatsDo::getFullShortUrl, requestParam.getFullShortUrl())
                        .eq(LinkOsStatsDo::getGid, originLinkDo.getGid())
                        .eq(LinkOsStatsDo::getDelFlag, 0);
                LinkOsStatsDo linkOsStatsDo = LinkOsStatsDo.builder()
                        .gid(requestParam.getGid())
                        .build();
                linkOsStatsMapper.update(linkOsStatsDo, linkOsStatsUpdateWrapper);
                LambdaUpdateWrapper<LinkBrowserStatsDo> linkBrowserStatsUpdateWrapper = Wrappers.lambdaUpdate(LinkBrowserStatsDo.class)
                        .eq(LinkBrowserStatsDo::getFullShortUrl, requestParam.getFullShortUrl())
                        .eq(LinkBrowserStatsDo::getGid, originLinkDo.getGid())
                        .eq(LinkBrowserStatsDo::getDelFlag, 0);
                LinkBrowserStatsDo linkBrowserStatsDo = LinkBrowserStatsDo.builder()
                        .gid(requestParam.getGid())
                        .build();
                linkBrowserStatsMapper.update(linkBrowserStatsDo, linkBrowserStatsUpdateWrapper);
                LambdaUpdateWrapper<LinkDeviceStatsDo> linkDeviceStatsUpdateWrapper = Wrappers.lambdaUpdate(LinkDeviceStatsDo.class)
                        .eq(LinkDeviceStatsDo::getFullShortUrl, requestParam.getFullShortUrl())
                        .eq(LinkDeviceStatsDo::getGid, originLinkDo.getGid())
                        .eq(LinkDeviceStatsDo::getDelFlag, 0);
                LinkDeviceStatsDo linkDeviceStatsDo = LinkDeviceStatsDo.builder()
                        .gid(requestParam.getGid())
                        .build();
                linkDeviceStatsMapper.update(linkDeviceStatsDo, linkDeviceStatsUpdateWrapper);
                LambdaUpdateWrapper<LinkNetworkStatsDo> linkNetworkStatsUpdateWrapper = Wrappers.lambdaUpdate(LinkNetworkStatsDo.class)
                        .eq(LinkNetworkStatsDo::getFullShortUrl, requestParam.getFullShortUrl())
                        .eq(LinkNetworkStatsDo::getGid, originLinkDo.getGid())
                        .eq(LinkNetworkStatsDo::getDelFlag, 0);
                LinkNetworkStatsDo linkNetworkStatsDo = LinkNetworkStatsDo.builder()
                        .gid(requestParam.getGid())
                        .build();
                linkNetworkStatsMapper.update(linkNetworkStatsDo, linkNetworkStatsUpdateWrapper);
                LambdaUpdateWrapper<LinkAccessLogsDo> linkAccessLogsUpdateWrapper = Wrappers.lambdaUpdate(LinkAccessLogsDo.class)
                        .eq(LinkAccessLogsDo::getFullShortUrl, requestParam.getFullShortUrl())
                        .eq(LinkAccessLogsDo::getGid, originLinkDo.getGid())
                        .eq(LinkAccessLogsDo::getDelFlag, 0);
                LinkAccessLogsDo linkAccessLogsDo = LinkAccessLogsDo.builder()
                        .gid(requestParam.getGid())
                        .build();
                linkAccessLogsMapper.update(linkAccessLogsDo, linkAccessLogsUpdateWrapper);
            } finally {
                rLock.unlock();
            }
        }
        //如果日期类型或者日期被修改了,直接将缓存删除
        //然后再去访问这个链接,就会访问数据库,然后发现为null,就会在redis里面加入一个null的key
        //但是这样有一个问题:如果此时将短链接修改成有效的日期,再去访问,那么就会因为这个null的key导致404
        if (!Objects.equals(originLinkDo.getValidDateType(), requestParam.getValidDateType()) ||
            !Objects.equals(originLinkDo.getValidDate(), requestParam.getValidDate())) {
            stringRedisTemplate.delete(RedisKeyConstant.GOTO_LINK_KEY + requestParam.getFullShortUrl());
            //如果原来的短链接是过期的短链接,那么你在修改它之前访问他会产生一个null key
            if (originLinkDo.getValidDate() != null && originLinkDo.getValidDate().before(new Date())) {
                //如果修改之后的日期是有效期之内的,将null key删除
                if (Objects.equals(requestParam.getValidDateType(), VailDateTypeEnum.PERMANENT.getType())
                    || requestParam.getValidDate().after(new Date())) {
                    stringRedisTemplate.delete(RedisKeyConstant.GOTO_LINK_IS_NULL_KEY + requestParam.getFullShortUrl());
                }
            }
        }

    }

    @SneakyThrows
    @Override
    public void restoreLink(String shortUrl, ServletRequest servletRequest, ServletResponse servletResponse) {
        String port = Optional.of(servletRequest.getServerPort())
                .filter(each -> !Objects.equals(each, 80))
                .map(String::valueOf)
                .map(each -> ":" + each)
                .orElse("");
        String fullShortUrl = servletRequest.getServerName() + port + "/" + shortUrl;

        String originUrl = stringRedisTemplate.opsForValue().get(RedisKeyConstant.GOTO_LINK_KEY + fullShortUrl);
        //如果缓存中直接就存在
        if (StrUtil.isNotBlank(originUrl)) {
            LinkStatsRecordDto statsRecord = buildLinkStatsRecordAndSetUser(fullShortUrl, servletRequest, servletResponse);
            linkStats(fullShortUrl, null, statsRecord);
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
                LinkStatsRecordDto statsRecord = buildLinkStatsRecordAndSetUser(fullShortUrl, servletRequest, servletResponse);
                linkStats(fullShortUrl, null, statsRecord);
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
            //记录短链接访问的相关数据
            LinkStatsRecordDto statsRecord = buildLinkStatsRecordAndSetUser(fullShortUrl, servletRequest, servletResponse);
            linkStats(fullShortUrl, linkGotoDo.getGid(), statsRecord);
            ((HttpServletResponse) servletResponse).sendRedirect(linkDo.getOriginUrl());
        } finally {
            lock.unlock();
        }
    }

    private LinkStatsRecordDto buildLinkStatsRecordAndSetUser(String fullShortUrl, ServletRequest servletRequest, ServletResponse servletResponse) {
        AtomicBoolean uvIsFirst = new AtomicBoolean();

        //通过cookies判断是否是同一个用户访问
        Cookie[] cookies = ((HttpServletRequest) servletRequest).getCookies();

        //如果使用的缓存找到的originUrl,那么就没有gid
        AtomicReference<String> uv = new AtomicReference<>();
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
        String ip = LinkUtil.getActualIp(((HttpServletRequest) servletRequest));
        String os = LinkUtil.getOs(((HttpServletRequest) servletRequest));
        String browser = LinkUtil.getBrowser(((HttpServletRequest) servletRequest));
        String device = LinkUtil.getDevice(((HttpServletRequest) servletRequest));
        String network = LinkUtil.getNetwork(((HttpServletRequest) servletRequest));
        Long uipAdded = stringRedisTemplate.opsForSet().add("short-link:stats:uip:" + fullShortUrl, ip);
        boolean uipFirstFlag = uipAdded != null && uipAdded > 0L;
        return LinkStatsRecordDto.builder()
                .fullShortUrl(fullShortUrl)
                .uv(uv.get())
                .uvFirstFlag(uvIsFirst.get())
                .uipFirstFlag(uipFirstFlag)
                .ip(ip)
                .os(os)
                .browser(browser)
                .device(device)
                .network(network)
                .build();
    }


    /**
     * 记录短链接访问的数据详情
     */

    public void linkStats(String fullShortUrl, String gid, LinkStatsRecordDto linkStatsRecordDto) {
        fullShortUrl = Optional.ofNullable(fullShortUrl).orElse(linkStatsRecordDto.getFullShortUrl());
        //获取读锁
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock(RedisKeyConstant.LOCK_GID_UPDATE_KEY + fullShortUrl);
        RLock rLock = readWriteLock.readLock();
        if (!rLock.tryLock()) {
            delayLinkStatsProducer.send(linkStatsRecordDto);
            return;
        }
        Date date = new Date();
        try {
            //如果是根据缓存跳转的,那么会缺少gid
            if (StrUtil.isBlank(gid)) {
                LambdaQueryWrapper<LinkGotoDo> wrapper = Wrappers.lambdaQuery(LinkGotoDo.class)
                        .eq(LinkGotoDo::getFullShortUrl, fullShortUrl);
                LinkGotoDo linkGotoDo = linkGotoMapper.selectOne(wrapper);
                gid = linkGotoDo.getGid();
            }
            //记录access表
            int hour = DateUtil.hour(date, true);
            Week week = DateUtil.dayOfWeekEnum(date);
            int weekday = week.getIso8601Value();
            LinkAccessStatsDo linkAccessStatsDo = LinkAccessStatsDo.builder()
                    .pv(1)
                    .uv(linkStatsRecordDto.getUvFirstFlag() ? 1 : 0)
                    .uip(linkStatsRecordDto.getUipFirstFlag() ? 1 : 0)
                    .weekday(weekday)
                    .date(date)
                    .fullShortUrl(fullShortUrl)
                    .gid(gid)
                    .hour(hour)
                    .build();
            linkAccessStatsDo.setCreateTime(date);
            linkAccessStatsDo.setUpdateTime(date);
            linkAccessStatsMapper.insertOrUpdate(linkAccessStatsDo);
            //记录地区
            Map<String, Object> map = new HashMap<>();
            map.put("key", linkLocaleStatsAmapKey);
            map.put("ip", linkStatsRecordDto.getIp());
            String resp = HttpUtil.get(amapApiUrl, map);
            JSONObject locale = JSON.parseObject(resp);
            String province, city, country;
            String infoCode = locale.getString("infocode");
            if (StrUtil.isNotBlank(infoCode) && Objects.equals(infoCode, "10000")) {
                province = locale.getString("province");
                city = locale.getString("city");
                country = locale.getString("country");
                boolean isNull = Objects.equals(province, "[]");
                if (isNull) {
                    province = "未知";
                    city = "未知";
                    country = "中国";
                }
                LinkLocaleStatsDo localeStatsDo = LinkLocaleStatsDo.builder()
                        .country(country)
                        .gid(gid)
                        .cnt(1)
                        .fullShortUrl(fullShortUrl)
                        .city(city)
                        .adcode(isNull ? "未知" : locale.getString("adcode"))
                        .province(province)
                        .date(date)
                        .build();
                linkLocaleStatsMapper.insertOrUpdate(localeStatsDo);
            } else {
                province = "未知";
                city = "未知";
                country = "中国";
            }
            //记录操作系统
            String os = linkStatsRecordDto.getOs();
            LinkOsStatsDo linkOsStatsDo = LinkOsStatsDo.builder()
                    .fullShortUrl(fullShortUrl)
                    .gid(gid)
                    .cnt(1)
                    .os(os)
                    .date(date).build();
            linkOsStatsDo.setCreateTime(date);
            linkOsStatsDo.setUpdateTime(date);
            linkOsStatsMapper.insertOrUpdate(linkOsStatsDo);
            //记录浏览器
            String browser = linkStatsRecordDto.getBrowser();
            LinkBrowserStatsDo linkBrowserStatsDo = LinkBrowserStatsDo.builder()
                    .fullShortUrl(fullShortUrl)
                    .gid(gid)
                    .cnt(1)
                    .browser(browser)
                    .date(date).build();
            linkBrowserStatsDo.setCreateTime(date);
            linkBrowserStatsDo.setUpdateTime(date);
            linkBrowserStatsMapper.insertOrUpdate(linkBrowserStatsDo);
            //记录设备
            String device = linkStatsRecordDto.getDevice();
            LinkDeviceStatsDo linkDeviceStatsDo = LinkDeviceStatsDo.builder()
                    .device(device)
                    .fullShortUrl(fullShortUrl)
                    .gid(gid)
                    .date(date)
                    .cnt(1)
                    .build();
            linkDeviceStatsDo.setCreateTime(date);
            linkDeviceStatsDo.setUpdateTime(date);
            linkDeviceStatsMapper.insertOrUpdate(linkDeviceStatsDo);
            //记录网络
            String network = linkStatsRecordDto.getNetwork();
            LinkNetworkStatsDo linkNetworkStatsDo = LinkNetworkStatsDo.builder()
                    .network(network)
                    .fullShortUrl(fullShortUrl)
                    .gid(gid)
                    .date(date)
                    .cnt(1)
                    .build();
            linkNetworkStatsDo.setCreateTime(date);
            linkNetworkStatsDo.setUpdateTime(date);
            linkNetworkStatsMapper.insertOrUpdate(linkNetworkStatsDo);
            //记录该条短链接的访问日志
            LinkAccessLogsDo linkAccessLogsDo = LinkAccessLogsDo.builder()
                    .ip(linkStatsRecordDto.getIp())
                    .network(network)
                    .device(device)
                    .locale(StrUtil.join("-", country, province, city))
                    .browser(browser)
                    .os(os)
                    .fullShortUrl(fullShortUrl)
                    .gid(gid)
                    .user(linkStatsRecordDto.getUv())
                    .build();
            linkAccessLogsDo.setCreateTime(date);
            linkAccessLogsDo.setUpdateTime(date);
            linkAccessLogsMapper.insert(linkAccessLogsDo);

            baseMapper.incrementStat(gid, fullShortUrl, 1, linkStatsRecordDto.getUvFirstFlag() ? 1 : 0, linkStatsRecordDto.getUipFirstFlag() ? 1 : 0);

            LinkStatsTodayDo linkStatsTodayDo = LinkStatsTodayDo.builder()
                    .todayPv(1)
                    .todayUip(linkStatsRecordDto.getUipFirstFlag() ? 1 : 0)
                    .todayUv(linkStatsRecordDto.getUvFirstFlag() ? 1 : 0)
                    .date(date)
                    .fullShortUrl(fullShortUrl)
                    .gid(gid)
                    .build();
            linkStatsTodayMapper.statsToday(linkStatsTodayDo);
        } catch (
                Exception e) {
            log.error("短链接访问量统计异常", e);
        } finally {
            rLock.unlock();
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

    private void verificationWhitelist(String url) {
        Boolean enable = gotoDomainWhiteListConfiguration.getEnable();
        if (enable == null || !enable) {
            return;
        }
        String domain = LinkUtil.extractDomain(url);
        if (StrUtil.isBlank(domain)) {
            throw new ClientException("短链接填写错误");
        }
        List<String> details = gotoDomainWhiteListConfiguration.getDetails();
        if (!details.contains(domain)) {
            throw new ClientException("请生成以下网站链接: " + gotoDomainWhiteListConfiguration.getNames());
        }
    }

}
