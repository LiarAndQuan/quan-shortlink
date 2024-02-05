package online.aquan.shortlink.project.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.aquan.shortlink.project.common.database.BaseDo;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LinkOsStatsDo extends BaseDo {
    /**
     * id
     */
    private Long id;

    /**
     * 完整短链接
     */
    private String fullShortUrl;

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 日期
     */
    private Date date;

    /**
     * 访问量
     */
    private Integer cnt;

    /**
     * 操作系统
     */
    private String os;
}
