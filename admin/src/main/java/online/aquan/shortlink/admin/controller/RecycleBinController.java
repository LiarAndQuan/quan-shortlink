package online.aquan.shortlink.admin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import online.aquan.shortlink.admin.common.convention.result.Result;
import online.aquan.shortlink.admin.common.convention.result.Results;
import online.aquan.shortlink.admin.remote.dto.LinkRemoteService;
import online.aquan.shortlink.admin.remote.dto.req.RecycleBinCreateReqDto;
import online.aquan.shortlink.admin.remote.dto.req.RecycleBinPageReqDto;
import online.aquan.shortlink.admin.remote.dto.req.RecycleBinRecoverReqDto;
import online.aquan.shortlink.admin.remote.dto.req.RecycleBinRemoveReqDto;
import online.aquan.shortlink.admin.remote.dto.resp.LinkPageRespDto;
import online.aquan.shortlink.admin.service.RecycleBinService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RecycleBinController {

    private final LinkRemoteService linkRemoteService = new LinkRemoteService() {
    };
    private final RecycleBinService recycleBinService;

    @PostMapping("/api/short-link/admin/v1/recycle-bin/save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinCreateReqDto requestParam) {
        return linkRemoteService.saveRecycleBin(requestParam);
    }

    @GetMapping("/api/short-link/admin/v1/recycle-bin/page")
    public Result<IPage<LinkPageRespDto>> pageRecycleBin(RecycleBinPageReqDto requestParam) {
        return recycleBinService.pageRecycleBin(requestParam);
    }

    @PostMapping("/api/short-link/admin/v1/recycle-bin/recover")
    public Result<Void> recoverRecycleBinLink(@RequestBody RecycleBinRecoverReqDto requestParam){
        linkRemoteService.recoverRecycleBinLink(requestParam);
        return Results.success();
    }

    @PostMapping("/api/short-link/admin/v1/recycle-bin/remove")
    public Result<Void> removeRecycleBinLink(@RequestBody RecycleBinRemoveReqDto requestParam){
        linkRemoteService.removeRecycleBinLink(requestParam);
        return Results.success();
    }
    
    
}
