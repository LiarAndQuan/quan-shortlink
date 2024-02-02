package online.aquan.shortlink.project.dao.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_link_goto")
public class LinkGotoDo {
    /**
     * id
     */
    private String id;

    /**
     * 短链接
     */
    private String shortUrl;

    /**
     * 短链接对应的gid
     */
    private String gid;
}
