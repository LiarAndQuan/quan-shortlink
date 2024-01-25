package online.aquan.shortlink.admin.dto.resp;


import lombok.Data;

@Data
public class GroupRepsDto {
    
    /**
     * 分组标识
     */
    private String gid;

    /**
     * 分组名称
     */
    private String name;

    /**
     * 短链接数量
     */
    private Integer shortLinkCount;
    
    /**
     * 分组排序
     */
    private Integer sortOrder;
}
