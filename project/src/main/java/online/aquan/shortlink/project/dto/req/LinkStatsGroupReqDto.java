package online.aquan.shortlink.project.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LinkStatsGroupReqDto {

    private String gid;

    private String startDate;

    private String endDate;
}
