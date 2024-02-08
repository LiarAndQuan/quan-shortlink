package online.aquan.shortlink.admin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import online.aquan.shortlink.admin.common.convention.result.Result;
import online.aquan.shortlink.admin.common.convention.result.Results;
import online.aquan.shortlink.admin.dto.req.LinkStatsReqDto;
import online.aquan.shortlink.admin.remote.dto.LinkRemoteService;
import online.aquan.shortlink.admin.remote.dto.req.LinkAccessRecordsPageReqDto;
import online.aquan.shortlink.admin.remote.dto.req.LinkStatsGroupReqDto;
import online.aquan.shortlink.admin.remote.dto.resp.LinkAccessRecordsPageRepsDto;
import online.aquan.shortlink.admin.remote.dto.resp.LinkStatsRespDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinkStatsController {

    private final LinkRemoteService linkRemoteService = new LinkRemoteService() {
    };

    @GetMapping("/api/short-link/admin/v1/stats")
    public Result<LinkStatsRespDto> getLinkStats(LinkStatsReqDto requestParam) {
        return linkRemoteService.getLinkStats(requestParam);
    }

    @GetMapping("/api/short-link/admin/v1/stats/group")
    public Result<LinkStatsRespDto> getGroupLinkStats(LinkStatsGroupReqDto requestParam) {
        return linkRemoteService.getGroupLinkStats(requestParam);
    }

    @GetMapping("/api/short-link/admin/v1/stats/access-record")
    public Result<IPage<LinkAccessRecordsPageRepsDto>> linkPageAccessRecords(LinkAccessRecordsPageReqDto requestParam) {
        return linkRemoteService.linkPageAccessRecords(requestParam);
    }

}
