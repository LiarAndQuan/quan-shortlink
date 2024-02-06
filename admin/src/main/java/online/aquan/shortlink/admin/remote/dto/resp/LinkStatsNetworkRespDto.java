package online.aquan.shortlink.admin.remote.dto.resp;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 网络访问详情
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinkStatsNetworkRespDto {

    /**
     * 统计
     */
    private Integer cnt;

    /**
     * 访问网络
     */
    private String network;

    /**
     * 占比
     */
    private Double ratio;
}
