package online.aquan.shortlink.admin.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import online.aquan.shortlink.admin.common.convention.result.Result;
import online.aquan.shortlink.admin.dto.req.LinkStatsReqDto;
import online.aquan.shortlink.admin.remote.LinkActualRemoteService;
import online.aquan.shortlink.admin.remote.dto.req.LinkAccessRecordsPageGroupReqDto;
import online.aquan.shortlink.admin.remote.dto.req.LinkAccessRecordsPageReqDto;
import online.aquan.shortlink.admin.remote.dto.req.LinkStatsGroupReqDto;
import online.aquan.shortlink.admin.remote.dto.resp.LinkAccessRecordsPageRepsDto;
import online.aquan.shortlink.admin.remote.dto.resp.LinkStatsRespDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="linkStatsControllerByAdmin")
@RequiredArgsConstructor
public class LinkStatsController {

    private final LinkActualRemoteService linkActualRemoteService;

    @GetMapping("/api/short-link/admin/v1/stats")
    public Result<LinkStatsRespDto> getLinkStats(LinkStatsReqDto requestParam) {
        return linkActualRemoteService.getLinkStats(requestParam);
    }

    @GetMapping("/api/short-link/admin/v1/stats/group")
    public Result<LinkStatsRespDto> getGroupLinkStats(LinkStatsGroupReqDto requestParam) {
        return linkActualRemoteService.getGroupLinkStats(requestParam);
    }

    @GetMapping("/api/short-link/admin/v1/stats/access-record")
    public Result<Page<LinkAccessRecordsPageRepsDto>> linkPageAccessRecords(LinkAccessRecordsPageReqDto requestParam) {
        return linkActualRemoteService.linkPageAccessRecords(requestParam);
    }

    @GetMapping("/api/short-link/admin/v1/stats/access-record/group")
    public Result<Page<LinkAccessRecordsPageRepsDto>> linkGroupPageAccessRecords(LinkAccessRecordsPageGroupReqDto requestParam) {
        return linkActualRemoteService.linkGroupPageAccessRecords(requestParam);
    }

}
