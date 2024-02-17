package online.aquan.shortlink.project.mq.consumer;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Week;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.aquan.shortlink.project.common.constant.RedisKeyConstant;
import online.aquan.shortlink.project.config.GotoDomainWhiteListConfiguration;
import online.aquan.shortlink.project.dao.entity.*;
import online.aquan.shortlink.project.dao.mapper.*;
import online.aquan.shortlink.project.dto.biz.LinkStatsRecordDto;
import online.aquan.shortlink.project.mq.producer.DelayLinkStatsProducer;
import online.aquan.shortlink.project.service.LinkStatsTodayService;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

import java.util.*;

import static online.aquan.shortlink.project.common.constant.LinkConstant.amapApiUrl;

@Slf4j
@Component
@RequiredArgsConstructor
public class LinkStatsSaveConsumer implements StreamListener<String, MapRecord<String, String, String>> {

    private final LinkGotoMapper linkGotoMapper;
    private final RedissonClient redissonClient;
    private final LinkAccessStatsMapper linkAccessStatsMapper;
    private final LinkLocaleStatsMapper linkLocaleStatsMapper;
    private final LinkOsStatsMapper linkOsStatsMapper;
    private final LinkBrowserStatsMapper linkBrowserStatsMapper;
    private final LinkAccessLogsMapper linkAccessLogsMapper;
    private final LinkDeviceStatsMapper linkDeviceStatsMapper;
    private final LinkNetworkStatsMapper linkNetworkStatsMapper;
    private final LinkStatsTodayMapper linkStatsTodayMapper;
    private final DelayLinkStatsProducer delayLinkStatsProducer;
    private final StringRedisTemplate stringRedisTemplate;
    private final LinkMapper linkMapper;

    @Value("${link.locale.stats.amap-key}")
    private String linkLocaleStatsAmapKey;

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        String stream = message.getStream();
        RecordId id = message.getId();
        Map<String, String> producerMap = message.getValue();
        String fullShortUrl = producerMap.get("fullShortUrl");
        if (StrUtil.isNotBlank(fullShortUrl)) {
            String gid = producerMap.get("gid");
            LinkStatsRecordDto statsRecord = JSON.parseObject(producerMap.get("statsRecord"), LinkStatsRecordDto.class);
            linkStats(fullShortUrl, gid, statsRecord);
        }
        stringRedisTemplate.opsForStream().delete(Objects.requireNonNull(stream), id.getValue());
    }

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

            linkMapper.incrementStat(gid, fullShortUrl, 1, linkStatsRecordDto.getUvFirstFlag() ? 1 : 0, linkStatsRecordDto.getUipFirstFlag() ? 1 : 0);

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

}
