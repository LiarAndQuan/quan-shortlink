package online.aquan.shortlink.admin.remote.dto.resp;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 地区访问详情
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinkStatsLocaleCNRespDto {


    /**
     * 地区
     */
    private String locale;


    /**
     * 次数
     */
    private Integer cnt;

    /**
     * 占比
     */
    private Double ratio;
}
