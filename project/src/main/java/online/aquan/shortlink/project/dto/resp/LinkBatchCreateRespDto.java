package online.aquan.shortlink.project.dto.resp;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LinkBatchCreateRespDto {

    /**
     * 成功数量
     */
    private Integer total;

    /**
     * 批量创建返回参数
     */
    private List<LinkBaseInfoRespDto> baseLinkInfos;
    ;
}
