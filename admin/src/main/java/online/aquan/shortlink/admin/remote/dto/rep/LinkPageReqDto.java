package online.aquan.shortlink.admin.remote.dto.rep;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class LinkPageReqDto extends Page {
    
    private String gid;
}
