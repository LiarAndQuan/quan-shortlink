package online.aquan.shortlink.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import online.aquan.shortlink.project.dao.entity.*;
import online.aquan.shortlink.project.dao.mapper.*;
import online.aquan.shortlink.project.dto.req.LinkAccessRecordsPageGroupReqDto;
import online.aquan.shortlink.project.dto.req.LinkAccessRecordsPageReqDto;
import online.aquan.shortlink.project.dto.req.LinkStatsGroupReqDto;
import online.aquan.shortlink.project.dto.req.LinkStatsReqDto;
import online.aquan.shortlink.project.dto.resp.*;
import online.aquan.shortlink.project.service.LinkStatsService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class LinkStatsServiceImpl implements LinkStatsService {

    private final LinkAccessStatsMapper linkAccessStatsMapper;
    private final LinkLocaleStatsMapper linkLocaleStatsMapper;
    private final LinkAccessLogsMapper linkAccessLogsMapper;
    private final LinkBrowserStatsMapper linkBrowserStatsMapper;
    private final LinkDeviceStatsMapper linkDeviceStatsMapper;
    private final LinkNetworkStatsMapper linkNetworkStatsMapper;
    private final LinkOsStatsMapper linkOsStatsMapper;

    @Override
    public LinkStatsRespDto getOneLinkStats(LinkStatsReqDto requestParam) {
        //每日基础访问详情
        List<LinkAccessStatsDo> linkAccessStatsDoList = linkAccessStatsMapper.getDailyLinkStats(requestParam);
        if (CollUtil.isEmpty(linkAccessStatsDoList)) {
            return null;
        }
        //获取这段时间内的pv,uv,uip
        LinkAccessStatsDo linkAccessStatsDo=linkAccessLogsMapper.getPvUvUip(requestParam);
        //获取每天的访问详情
        List<LinkStatsAccessDailyRespDto> allDailyList = new ArrayList<>();
        //首先将这个时间段里面所有的日期都取出来
        List<DateTime> dateRange = DateUtil.rangeToList(DateUtil.parse(requestParam.getStartDate())
                , DateUtil.parse(requestParam.getEndDate()),
                DateField.DAY_OF_MONTH).stream().toList();
        //然后遍历,如果之前集合内有记录那么就取记录,没有就置为0
        dateRange.forEach(each -> linkAccessStatsDoList.stream()
                .filter(item -> Objects.equals(each, item.getDate()))
                .findFirst()
                .ifPresentOrElse(
                        item -> {
                            LinkStatsAccessDailyRespDto dailyRespDto = LinkStatsAccessDailyRespDto.builder()
                                    .uv(item.getUv())
                                    .pv(item.getPv())
                                    .uip(item.getUip())
                                    .date(item.getDate()).build();
                            allDailyList.add(dailyRespDto);
                        }, () -> {
                            LinkStatsAccessDailyRespDto dailyRespDto = LinkStatsAccessDailyRespDto.builder()
                                    .uv(0)
                                    .pv(0)
                                    .uip(0)
                                    .date(each).build();
                            allDailyList.add(dailyRespDto);
                        }
                ));
        //地区访问详情
        List<LinkStatsLocaleCNRespDto> allLocaleCNRespDtos = new ArrayList<>();
        List<LinkLocaleStatsDo> linkLocaleStatsDos = linkLocaleStatsMapper.getLocaleAndCnt(requestParam);
        //获取所有地区内的访问数之和
        int localeCntSum = linkLocaleStatsDos.stream().mapToInt(LinkLocaleStatsDo::getCnt).sum();
        linkLocaleStatsDos.forEach(each -> {
            double ratio = (double) each.getCnt() / localeCntSum;
            double actualRatio = Math.round(ratio * 100) / 100.0;
            LinkStatsLocaleCNRespDto localeCNRespDto = LinkStatsLocaleCNRespDto.builder()
                    .ratio(actualRatio)
                    .cnt(each.getCnt())
                    .locale(each.getProvince()).build();
            allLocaleCNRespDtos.add(localeCNRespDto);
        });
        //获取小时访问详情
        List<Integer> allHoursResp = new ArrayList<>(24);
        List<LinkAccessStatsDo> linkHourSAndCnt = linkAccessStatsMapper.getHoursAndCnt(requestParam);
        for (int i = 0; i < 24; i++) {
            AtomicInteger nowHour = new AtomicInteger(i);
            Integer hourCnt = linkHourSAndCnt.stream().filter(each -> Objects.equals(nowHour.get(), each.getHour())).findFirst()
                    .map(LinkAccessStatsDo::getPv).orElse(0);
            allHoursResp.add(hourCnt);
        }
        //高频ip访问详情
        List<LinkStatsTopIpRespDto> allHotIpCnt = new ArrayList<>();
        List<HashMap<String, Object>> linkAccessLogsDosMap = linkAccessLogsMapper.getTopIpAndCnt(requestParam);
        linkAccessLogsDosMap.forEach(
                each -> {
                    LinkStatsTopIpRespDto linkStatsTopIpRespDto = LinkStatsTopIpRespDto.builder()
                            .ip(each.get("ip").toString())
                            .cnt(Integer.parseInt(each.get("count").toString())).build();
                    allHotIpCnt.add(linkStatsTopIpRespDto);
                }
        );
        //一周的访问详情
        List<Integer> allWeekdayCnt = new ArrayList<>();
        List<LinkAccessStatsDo> linkAccessStatsDos = linkAccessStatsMapper.getWeekdayCnt(requestParam);
        for (int i = 1; i <= 7; i++) {
            AtomicInteger nowWeekday = new AtomicInteger(i);
            Integer weekdayCnt = linkAccessStatsDos.stream().filter(each -> Objects.equals(nowWeekday.get(), each.getWeekday())).findFirst()
                    .map(LinkAccessStatsDo::getPv).orElse(0);
            allWeekdayCnt.add(weekdayCnt);
        }
        //浏览器访问详情
        List<LinkStatsBrowserRespDto> allBrowserAndCnt = new ArrayList<>();
        List<LinkBrowserStatsDo> linkBrowserStatsDos = linkBrowserStatsMapper.getBrowserAndCnt(requestParam);
        int browserSum = linkBrowserStatsDos.stream().mapToInt(LinkBrowserStatsDo::getCnt).sum();
        linkBrowserStatsDos.forEach(
                each -> {
                    double ratio = (double) each.getCnt() / browserSum;
                    double actualRatio = Math.round(ratio * 100) / 100.0;
                    LinkStatsBrowserRespDto linkStatsBrowserRespDto = LinkStatsBrowserRespDto.builder()
                            .ratio(actualRatio)
                            .browser(each.getBrowser())
                            .cnt(each.getCnt()).build();
                    allBrowserAndCnt.add(linkStatsBrowserRespDto);
                }
        );
        //操作系统访问详情
        List<LinkStatsOsRespDto> allOsAndCnt = new ArrayList<>();
        List<LinkOsStatsDo> linkOsStatsDos = linkOsStatsMapper.getOsAndCnt(requestParam);
        int OsSum = linkOsStatsDos.stream().mapToInt(LinkOsStatsDo::getCnt).sum();
        linkOsStatsDos.forEach(
                each -> {
                    double ratio = (double) each.getCnt() / OsSum;
                    double actualRatio = Math.round(ratio * 100) / 100.0;
                    LinkStatsOsRespDto linkStatsOsRespDto = LinkStatsOsRespDto.builder()
                            .ratio(actualRatio)
                            .os(each.getOs())
                            .cnt(each.getCnt()).build();
                    allOsAndCnt.add(linkStatsOsRespDto);
                }
        );
        //访问设备统计详情
        List<LinkStatsDeviceRespDto> allDeviceAndCnt = new ArrayList<>();
        List<LinkDeviceStatsDo> linkDeviceStatsDos = linkDeviceStatsMapper.getDeviceAndCnt(requestParam);
        int deviceSum = linkDeviceStatsDos.stream().mapToInt(LinkDeviceStatsDo::getCnt).sum();
        linkDeviceStatsDos.forEach(
                each -> {
                    double ratio = (double) each.getCnt() / deviceSum;
                    double actualRatio = Math.round(ratio * 100) / 100.0;
                    LinkStatsDeviceRespDto linkStatsDeviceRespDto = LinkStatsDeviceRespDto.builder()
                            .ratio(actualRatio)
                            .device(each.getDevice())
                            .cnt(each.getCnt()).build();
                    allDeviceAndCnt.add(linkStatsDeviceRespDto);
                }
        );

        //访问网络详情统计
        List<LinkStatsNetworkRespDto> allNetworkAndCnt = new ArrayList<>();
        List<LinkNetworkStatsDo> linkNetworkStatsDos = linkNetworkStatsMapper.getNetworkAndCnt(requestParam);
        int networkSum = linkNetworkStatsDos.stream().mapToInt(LinkNetworkStatsDo::getCnt).sum();
        linkNetworkStatsDos.forEach(
                each -> {
                    double ratio = (double) each.getCnt() / networkSum;
                    double actualRatio = Math.round(ratio * 100) / 100.0;
                    LinkStatsNetworkRespDto linkStatsNetworkRespDto = LinkStatsNetworkRespDto.builder()
                            .ratio(actualRatio)
                            .network(each.getNetwork())
                            .cnt(each.getCnt()).build();
                    allNetworkAndCnt.add(linkStatsNetworkRespDto);
                }
        );
        //访客类型
        List<LinkStatsUvRespDto> allUvAndCnt = new ArrayList<>();
        HashMap<String, Object> uvAndCntMap = linkAccessLogsMapper.getUvAndCnt(requestParam);
        int oldUserCount = Integer.parseInt(Optional.ofNullable(uvAndCntMap)
                .map(each -> each.get("oldUserCount"))
                .map(Object::toString).orElse("0"));
        int newUserCount = Integer.parseInt(Optional.ofNullable(uvAndCntMap)
                .map(each -> each.get("newUserCount"))
                .map(Object::toString).orElse("0"));
        int uvSum = oldUserCount + newUserCount;
        double oldRatio = (double) oldUserCount / uvSum;
        double oldActualRatio = Math.round(oldRatio * 100) / 100.0;
        double newRatio = (double) newUserCount / uvSum;
        double newActualRatio = Math.round(newRatio * 100) / 100.0;
        LinkStatsUvRespDto oldUvRespDto = LinkStatsUvRespDto.builder()
                .uvType("老用户")
                .ratio(oldActualRatio)
                .cnt(oldUserCount).build();
        allUvAndCnt.add(oldUvRespDto);
        LinkStatsUvRespDto newUvRespDto = LinkStatsUvRespDto.builder()
                .uvType("新用户")
                .ratio(newActualRatio)
                .cnt(newUserCount).build();
        allUvAndCnt.add(newUvRespDto);
        return LinkStatsRespDto.builder()
                .pv(linkAccessStatsDo.getPv())
                .uv(linkAccessStatsDo.getUv())
                .uip(linkAccessStatsDo.getUip())
                .browserStats(allBrowserAndCnt)
                .deviceStats(allDeviceAndCnt)
                .localeCnStats(allLocaleCNRespDtos)
                .networkStats(allNetworkAndCnt)
                .topIpStats(allHotIpCnt)
                .hourStats(allHoursResp)
                .daily(allDailyList)
                .osStats(allOsAndCnt)
                .uvTypeStats(allUvAndCnt)
                .weekdayStats(allWeekdayCnt)
                .build();
    }

    /**
     * 获取一个分组下面的访问详情
     */
    @Override
    public LinkStatsRespDto getGroupLinkStats(LinkStatsGroupReqDto requestParam) {
        //每日基础访问详情
        List<LinkAccessStatsDo> linkAccessStatsDoList = linkAccessStatsMapper.getGroupDailyLinkStats(requestParam);
        if (CollUtil.isEmpty(linkAccessStatsDoList)) {
            return null;
        }
        //获取这段时间内的pv,uv,uip
        LinkAccessStatsDo linkAccessStatsDo=linkAccessLogsMapper.getGroupPvUvUip(requestParam);
        //获取每天的访问详情
        List<LinkStatsAccessDailyRespDto> allDailyList = new ArrayList<>();
        //首先将这个时间段里面所有的日期都取出来
        List<DateTime> dateRange = DateUtil.rangeToList(DateUtil.parse(requestParam.getStartDate())
                , DateUtil.parse(requestParam.getEndDate()),
                DateField.DAY_OF_MONTH).stream().toList();
        //然后遍历,如果之前集合内有记录那么就取记录,没有就置为0
        dateRange.forEach(each -> linkAccessStatsDoList.stream()
                .filter(item -> Objects.equals(each, item.getDate()))
                .findFirst()
                .ifPresentOrElse(
                        item -> {
                            LinkStatsAccessDailyRespDto dailyRespDto = LinkStatsAccessDailyRespDto.builder()
                                    .uv(item.getUv())
                                    .pv(item.getPv())
                                    .uip(item.getUip())
                                    .date(item.getDate()).build();
                            allDailyList.add(dailyRespDto);
                        }, () -> {
                            LinkStatsAccessDailyRespDto dailyRespDto = LinkStatsAccessDailyRespDto.builder()
                                    .uv(0)
                                    .pv(0)
                                    .uip(0)
                                    .date(each).build();
                            allDailyList.add(dailyRespDto);
                        }
                ));
        //地区访问详情
        List<LinkStatsLocaleCNRespDto> allLocaleCNRespDtos = new ArrayList<>();
        List<LinkLocaleStatsDo> linkLocaleStatsDos = linkLocaleStatsMapper.getGroupLocaleAndCnt(requestParam);
        //获取所有地区内的访问数之和
        int localeCntSum = linkLocaleStatsDos.stream().mapToInt(LinkLocaleStatsDo::getCnt).sum();
        linkLocaleStatsDos.forEach(each -> {
            double ratio = (double) each.getCnt() / localeCntSum;
            double actualRatio = Math.round(ratio * 100) / 100.0;
            LinkStatsLocaleCNRespDto localeCNRespDto = LinkStatsLocaleCNRespDto.builder()
                    .ratio(actualRatio)
                    .cnt(each.getCnt())
                    .locale(each.getProvince()).build();
            allLocaleCNRespDtos.add(localeCNRespDto);
        });
        //获取小时访问详情
        List<Integer> allHoursResp = new ArrayList<>(24);
        List<LinkAccessStatsDo> linkHourSAndCnt = linkAccessStatsMapper.getGroupHoursAndCnt(requestParam);
        for (int i = 0; i < 24; i++) {
            AtomicInteger nowHour = new AtomicInteger(i);
            Integer hourCnt = linkHourSAndCnt.stream().filter(each -> Objects.equals(nowHour.get(), each.getHour())).findFirst()
                    .map(LinkAccessStatsDo::getPv).orElse(0);
            allHoursResp.add(hourCnt);
        }
        //高频ip访问详情
        List<LinkStatsTopIpRespDto> allHotIpCnt = new ArrayList<>();
        List<HashMap<String, Object>> linkAccessLogsDosMap = linkAccessLogsMapper.getGroupTopIpAndCnt(requestParam);
        linkAccessLogsDosMap.forEach(
                each -> {
                    LinkStatsTopIpRespDto linkStatsTopIpRespDto = LinkStatsTopIpRespDto.builder()
                            .ip(each.get("ip").toString())
                            .cnt(Integer.parseInt(each.get("count").toString())).build();
                    allHotIpCnt.add(linkStatsTopIpRespDto);
                }
        );
        //一周的访问详情
        List<Integer> allWeekdayCnt = new ArrayList<>();
        List<LinkAccessStatsDo> linkAccessStatsDos = linkAccessStatsMapper.getGroupWeekdayCnt(requestParam);
        for (int i = 1; i <= 7; i++) {
            AtomicInteger nowWeekday = new AtomicInteger(i);
            Integer weekdayCnt = linkAccessStatsDos.stream().filter(each -> Objects.equals(nowWeekday.get(), each.getWeekday())).findFirst()
                    .map(LinkAccessStatsDo::getPv).orElse(0);
            allWeekdayCnt.add(weekdayCnt);
        }
        //浏览器访问详情
        List<LinkStatsBrowserRespDto> allBrowserAndCnt = new ArrayList<>();
        List<LinkBrowserStatsDo> linkBrowserStatsDos = linkBrowserStatsMapper.getGroupBrowserAndCnt(requestParam);
        int browserSum = linkBrowserStatsDos.stream().mapToInt(LinkBrowserStatsDo::getCnt).sum();
        linkBrowserStatsDos.forEach(
                each -> {
                    double ratio = (double) each.getCnt() / browserSum;
                    double actualRatio = Math.round(ratio * 100) / 100.0;
                    LinkStatsBrowserRespDto linkStatsBrowserRespDto = LinkStatsBrowserRespDto.builder()
                            .ratio(actualRatio)
                            .browser(each.getBrowser())
                            .cnt(each.getCnt()).build();
                    allBrowserAndCnt.add(linkStatsBrowserRespDto);
                }
        );
        //操作系统访问详情
        List<LinkStatsOsRespDto> allOsAndCnt = new ArrayList<>();
        List<LinkOsStatsDo> linkOsStatsDos = linkOsStatsMapper.getGroupOsAndCnt(requestParam);
        int OsSum = linkOsStatsDos.stream().mapToInt(LinkOsStatsDo::getCnt).sum();
        linkOsStatsDos.forEach(
                each -> {
                    double ratio = (double) each.getCnt() / OsSum;
                    double actualRatio = Math.round(ratio * 100) / 100.0;
                    LinkStatsOsRespDto linkStatsOsRespDto = LinkStatsOsRespDto.builder()
                            .ratio(actualRatio)
                            .os(each.getOs())
                            .cnt(each.getCnt()).build();
                    allOsAndCnt.add(linkStatsOsRespDto);
                }
        );
        
        //访问设备统计详情
        List<LinkStatsDeviceRespDto> allDeviceAndCnt = new ArrayList<>();
        List<LinkDeviceStatsDo> linkDeviceStatsDos = linkDeviceStatsMapper.getGroupDeviceAndCnt(requestParam);
        int deviceSum = linkDeviceStatsDos.stream().mapToInt(LinkDeviceStatsDo::getCnt).sum();
        linkDeviceStatsDos.forEach(
                each -> {
                    double ratio = (double) each.getCnt() / deviceSum;
                    double actualRatio = Math.round(ratio * 100) / 100.0;
                    LinkStatsDeviceRespDto linkStatsDeviceRespDto = LinkStatsDeviceRespDto.builder()
                            .ratio(actualRatio)
                            .device(each.getDevice())
                            .cnt(each.getCnt()).build();
                    allDeviceAndCnt.add(linkStatsDeviceRespDto);
                }
        );

        //访问网络详情统计
        List<LinkStatsNetworkRespDto> allNetworkAndCnt = new ArrayList<>();
        List<LinkNetworkStatsDo> linkNetworkStatsDos = linkNetworkStatsMapper.getGroupNetworkAndCnt(requestParam);
        int networkSum = linkNetworkStatsDos.stream().mapToInt(LinkNetworkStatsDo::getCnt).sum();
        linkNetworkStatsDos.forEach(
                each -> {
                    double ratio = (double) each.getCnt() / networkSum;
                    double actualRatio = Math.round(ratio * 100) / 100.0;
                    LinkStatsNetworkRespDto linkStatsNetworkRespDto = LinkStatsNetworkRespDto.builder()
                            .ratio(actualRatio)
                            .network(each.getNetwork())
                            .cnt(each.getCnt()).build();
                    allNetworkAndCnt.add(linkStatsNetworkRespDto);
                }
        );
        
        return LinkStatsRespDto.builder()
                .pv(linkAccessStatsDo.getPv())
                .uv(linkAccessStatsDo.getUv())
                .uip(linkAccessStatsDo.getUip())
                .browserStats(allBrowserAndCnt)
                .deviceStats(allDeviceAndCnt)
                .localeCnStats(allLocaleCNRespDtos)
                .networkStats(allNetworkAndCnt)
                .topIpStats(allHotIpCnt)
                .hourStats(allHoursResp)
                .daily(allDailyList)
                .osStats(allOsAndCnt)
                .weekdayStats(allWeekdayCnt)
                .build();
    }
    @Override
    public IPage<LinkAccessRecordsPageRepsDto> linkPageAccessRecords(LinkAccessRecordsPageReqDto requestParam) {
        //首先获取原始的数据
        LambdaQueryWrapper<LinkAccessLogsDo> wrapper = Wrappers.lambdaQuery(LinkAccessLogsDo.class)
                .eq(LinkAccessLogsDo::getGid, requestParam.getGid())
                .eq(LinkAccessLogsDo::getFullShortUrl, requestParam.getFullShortUrl())
                .between(LinkAccessLogsDo::getCreateTime, requestParam.getStartDate(), requestParam.getEndDate())
                .orderByDesc(LinkAccessLogsDo::getCreateTime);
        IPage<LinkAccessLogsDo> pageAccessRecords = linkAccessLogsMapper.selectPage(requestParam, wrapper);
        //转化成resp对象
        IPage<LinkAccessRecordsPageRepsDto> result = pageAccessRecords.convert(each -> BeanUtil.toBean(each, LinkAccessRecordsPageRepsDto.class));
        //获取到所有的用户
        List<String> userList = result.getRecords().stream().map(LinkAccessRecordsPageRepsDto::getUser).toList();
        if (CollUtil.isEmpty(userList)) {
            return result;
        }
        //判断是新用户还是老用户
        List<Map<String, Object>> uvTypeMap = linkAccessLogsMapper.getUvType(
                requestParam.getGid(),
                requestParam.getFullShortUrl(),
                requestParam.getStartDate(),
                requestParam.getEndDate(),
                userList
        );
        result.getRecords().forEach(
                each -> {
                    String uvType = uvTypeMap.stream().filter(
                                    item -> Objects.equals(each.getUser(), item.get("user"))
                            ).findFirst()
                            .map(item -> item.get("uvType"))
                            .map(Object::toString)
                            .orElse("老访客");
                    each.setUvType(uvType);
                }
        );
        return result;

    }

    @Override
    public IPage<LinkAccessRecordsPageRepsDto> linkGroupPageAccessRecords(LinkAccessRecordsPageGroupReqDto requestParam) {
        //首先获取原始的数据
        LambdaQueryWrapper<LinkAccessLogsDo> wrapper = Wrappers.lambdaQuery(LinkAccessLogsDo.class)
                .eq(LinkAccessLogsDo::getGid, requestParam.getGid())
                .between(LinkAccessLogsDo::getCreateTime, requestParam.getStartDate(), requestParam.getEndDate()+" 23:59:59")
                .orderByDesc(LinkAccessLogsDo::getCreateTime);
        IPage<LinkAccessLogsDo> pageAccessRecords = linkAccessLogsMapper.selectPage(requestParam, wrapper);
        //转化成resp对象
        IPage<LinkAccessRecordsPageRepsDto> result = pageAccessRecords.convert(each -> BeanUtil.toBean(each, LinkAccessRecordsPageRepsDto.class));
        //获取到所有的用户
        List<String> userList = result.getRecords().stream().map(LinkAccessRecordsPageRepsDto::getUser).toList();
        if (CollUtil.isEmpty(userList)) {
            return result;
        }
        //判断是新用户还是老用户
        List<Map<String, Object>> uvTypeMap = linkAccessLogsMapper.getUvTypeGroup(
                requestParam.getGid(),
                requestParam.getStartDate(),
                requestParam.getEndDate(),
                userList
        );
        result.getRecords().forEach(
                each -> {
                    String uvType = uvTypeMap.stream().filter(
                                    item -> Objects.equals(each.getUser(), item.get("user"))
                            ).findFirst()
                            .map(item -> item.get("uvType"))
                            .map(Object::toString)
                            .orElse("老访客");
                    each.setUvType(uvType);
                }
        );
        return result;
    }
}