package online.aquan.shortlink.project.dto.resp;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 操作系统访问详情
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinkStatsOsRespDto {


    /**
     * 统计
     */
    private Integer cnt;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 占比
     */
    private Double ratio;
}
