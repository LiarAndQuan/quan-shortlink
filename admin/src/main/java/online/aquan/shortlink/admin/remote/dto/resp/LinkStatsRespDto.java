package online.aquan.shortlink.admin.remote.dto.resp;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 单个短链接访问各参数在一定时间段内汇总
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinkStatsRespDto {

    /**
     * 总访问量
     */
    private Integer pv;

    /**
     * 总独立访客数
     */
    private Integer uv;

    /**
     * 总独立IP数
     */
    private Integer uip;

    /**
     * 每日基础访问详情
     */
    private List<LinkStatsAccessDailyRespDto> daily;

    /**
     * 地区访问详情（仅国内）
     */
    private List<LinkStatsLocaleCNRespDto> localeCnStats;

    /**
     * 小时访问详情
     */
    private List<Integer> hourStats;

    /**
     * 高频访问IP详情
     */
    private List<LinkStatsTopIpRespDto> topIpStats;

    /**
     * 一周访问详情
     */
    private List<Integer> weekdayStats;

    /**
     * 浏览器访问详情
     */
    private List<LinkStatsBrowserRespDto> browserStats;

    /**
     * 操作系统访问详情
     */
    private List<LinkStatsOsRespDto> osStats;

    /**
     * 访客访问类型详情
     */
    private List<LinkStatsUvRespDto> uvTypeStats;

    /**
     * 访问设备类型详情
     */
    private List<LinkStatsDeviceRespDto> deviceStats;

    /**
     * 访问网络类型详情
     */
    private List<LinkStatsNetworkRespDto> networkStats;
    
}
