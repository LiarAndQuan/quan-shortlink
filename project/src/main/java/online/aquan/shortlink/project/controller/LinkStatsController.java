package online.aquan.shortlink.project.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import online.aquan.shortlink.project.common.convention.result.Result;
import online.aquan.shortlink.project.common.convention.result.Results;
import online.aquan.shortlink.project.dto.req.LinkAccessRecordsPageReqDto;
import online.aquan.shortlink.project.dto.req.LinkStatsGroupReqDto;
import online.aquan.shortlink.project.dto.req.LinkStatsReqDto;
import online.aquan.shortlink.project.dto.resp.LinkAccessRecordsPageRepsDto;
import online.aquan.shortlink.project.dto.resp.LinkStatsRespDto;
import online.aquan.shortlink.project.service.LinkStatsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LinkStatsController {


    private final LinkStatsService linkStatsService;

    @GetMapping("/api/short-link/v1/stats")
    public Result<LinkStatsRespDto> getOneLinkStats(LinkStatsReqDto requestParam) {
        return Results.success(linkStatsService.getOneLinkStats(requestParam));
    }
    @GetMapping("/api/short-link/v1/stats/group")
    public Result<LinkStatsRespDto> getGroupLinkStats(LinkStatsGroupReqDto requestParam) {
        return Results.success(linkStatsService.getGroupLinkStats(requestParam));
    }
    

    @GetMapping("/api/short-link/v1/stats/access-record")
    public Result<IPage<LinkAccessRecordsPageRepsDto>> linkPageAccessRecords(LinkAccessRecordsPageReqDto requestParam) {
        return Results.success(linkStatsService.linkPageAccessRecords(requestParam));
    }

}
