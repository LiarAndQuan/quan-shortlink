package online.aquan.shortlink.project.service;

import online.aquan.shortlink.project.dto.req.LinkStatsReqDto;
import online.aquan.shortlink.project.dto.resp.LinkStatsRespDto;

public interface LinkStatsService {
    LinkStatsRespDto getOneLinkStats(LinkStatsReqDto requestParam);
}
