package online.aquan.shortlink.admin.dto.req;


import lombok.Data;

@Data
public class LinkStatsReqDto {

    private String gid;

    private String fullShortUrl;

    private String startDate;
    
    private String endDate;
}
