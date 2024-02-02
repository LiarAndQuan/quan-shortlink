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
     * 完整的短链接
     */
    private String fullShortUrl;

    /**
     * 短链接对应的gid
     */
    private String gid;
}
