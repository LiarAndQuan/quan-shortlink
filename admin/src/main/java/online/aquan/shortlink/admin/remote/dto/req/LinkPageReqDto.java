package online.aquan.shortlink.admin.remote.dto.req;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class LinkPageReqDto extends Page {
    
    private String gid;
    
    
    private String orderTag;
}
