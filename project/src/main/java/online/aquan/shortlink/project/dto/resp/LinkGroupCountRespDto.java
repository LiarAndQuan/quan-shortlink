package online.aquan.shortlink.project.dto.resp;


import lombok.Data;

@Data
public class LinkGroupCountRespDto {

    /**
     * 分组id
     */
    private String gid;

    /**
     * 分组下链接数量
     */
    private Integer shortLinkCount;
}
