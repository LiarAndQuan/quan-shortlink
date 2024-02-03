package online.aquan.shortlink.project.dto.req;


import lombok.Data;

@Data
public class RecycleBinRemoveReqDto {
    
    private String gid;
    
    private String fullShortUrl;
}
