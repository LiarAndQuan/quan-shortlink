package online.aquan.shortlink.admin.remote.dto.req;


import lombok.Data;

@Data
public class RecycleBinRemoveReqDto {
    
    private String gid;
    
    private String fullShortUrl;
}
