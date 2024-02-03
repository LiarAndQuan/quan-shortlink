package online.aquan.shortlink.project.dto.rep;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import online.aquan.shortlink.project.dao.entity.LinkDo;

import java.util.List;

@Data
public class RecycleBinPageReqDto extends Page<LinkDo> {

    /**
     * gid列表
     */
    private List<String> gidList;
}
