package online.aquan.shortlink.admin.remote.dto.req;


import lombok.Data;

@Data
public class RecycleBinRecoverReqDto {
    
    private String gid;
    
    private String fullShortUrl;
}
