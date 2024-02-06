package online.aquan.shortlink.project.dto.req;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LinkStatsReqDto {

    private String gid;

    private String fullShortUrl;

    private String startDate;
    
    private String endDate;
}
