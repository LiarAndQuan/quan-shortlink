package online.aquan.shortlink.project.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import online.aquan.shortlink.project.dao.entity.LinkAccessLogsDo;


@Data
public class LinkAccessRecordsPageReqDto extends Page<LinkAccessLogsDo> {

    /**
     * 完整短链接
     */
    private String fullShortUrl;

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;
    
}
