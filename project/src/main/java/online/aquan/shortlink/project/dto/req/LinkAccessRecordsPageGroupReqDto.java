package online.aquan.shortlink.project.dto.req;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.aquan.shortlink.project.dao.entity.LinkAccessLogsDo;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LinkAccessRecordsPageGroupReqDto extends Page<LinkAccessLogsDo> {


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
