package online.aquan.shortlink.admin.remote.dto.resp;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 新老访客访问详情
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinkStatsUvRespDto {

    /**
     * 统计
     */
    private Integer cnt;

    /**
     * 访客类型
     */
    private String uvType;

    /**
     * 占比
     */
    private Double ratio;
}
