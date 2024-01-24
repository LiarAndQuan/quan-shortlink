package online.aquan.shortlink.project.dto.rep;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import online.aquan.shortlink.project.dao.entity.LinkDo;


@Data
public class LinkPageReqDto extends Page<LinkDo> {
    
    private String gid;
}
