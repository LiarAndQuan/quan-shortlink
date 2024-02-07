package online.aquan.shortlink.project.dao.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.aquan.shortlink.project.common.database.BaseDo;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_link_access_logs")
@Builder
public class LinkAccessLogsDo extends BaseDo {
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
     * 用户信息
     */
    private String user;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * ip
     */
    private String ip;

    /**
     * 地区
     */
    private String locale;

    private String device;

    private String network;
    /**
     * 操作系统
     */
    private String os;

}
