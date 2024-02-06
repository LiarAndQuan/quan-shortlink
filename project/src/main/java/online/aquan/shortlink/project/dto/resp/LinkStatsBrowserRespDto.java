package online.aquan.shortlink.project.dto.resp;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 浏览器访问详情
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinkStatsBrowserRespDto {

    /**
     * 统计
     */
    private Integer cnt;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 占比
     */
    private Double ratio;
}
