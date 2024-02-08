package online.aquan.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import online.aquan.shortlink.project.dto.req.LinkAccessRecordsPageReqDto;
import online.aquan.shortlink.project.dto.req.LinkStatsGroupReqDto;
import online.aquan.shortlink.project.dto.req.LinkStatsReqDto;
import online.aquan.shortlink.project.dto.resp.LinkAccessRecordsPageRepsDto;
import online.aquan.shortlink.project.dto.resp.LinkStatsRespDto;

public interface LinkStatsService {
    
    LinkStatsRespDto getOneLinkStats(LinkStatsReqDto requestParam);

    LinkStatsRespDto getGroupLinkStats(LinkStatsGroupReqDto requestParam);

    IPage<LinkAccessRecordsPageRepsDto> linkPageAccessRecords(LinkAccessRecordsPageReqDto requestParam);
}
