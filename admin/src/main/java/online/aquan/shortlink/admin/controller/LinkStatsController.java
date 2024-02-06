package online.aquan.shortlink.admin.controller;


import online.aquan.shortlink.admin.common.convention.result.Result;
import online.aquan.shortlink.admin.dto.req.LinkStatsReqDto;
import online.aquan.shortlink.admin.remote.dto.LinkRemoteService;
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
}
