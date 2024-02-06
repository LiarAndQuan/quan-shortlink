package online.aquan.shortlink.admin.remote.dto.resp;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 高频访问详情
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinkStatsTopIpRespDto {

    /**
     * 统计
     */
    private Integer cnt;

    /**
     * IP
     */
    private String ip;
}
