package online.aquan.shortlink.admin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import online.aquan.shortlink.admin.common.convention.result.Result;
import online.aquan.shortlink.admin.common.convention.result.Results;
import online.aquan.shortlink.admin.remote.LinkActualRemoteService;
import online.aquan.shortlink.admin.remote.LinkRemoteService;
import online.aquan.shortlink.admin.remote.dto.req.RecycleBinCreateReqDto;
import online.aquan.shortlink.admin.remote.dto.req.RecycleBinPageReqDto;
import online.aquan.shortlink.admin.remote.dto.req.RecycleBinRecoverReqDto;
import online.aquan.shortlink.admin.remote.dto.req.RecycleBinRemoveReqDto;
import online.aquan.shortlink.admin.remote.dto.resp.LinkPageRespDto;
import online.aquan.shortlink.admin.service.RecycleBinService;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RecycleBinController {

    private final LinkActualRemoteService linkActualRemoteService;
    private final RecycleBinService recycleBinService;

    @PostMapping("/api/short-link/admin/v1/recycle-bin/save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinCreateReqDto requestParam) {
        return linkActualRemoteService.saveRecycleBin(requestParam);
    }

    @GetMapping("/api/short-link/admin/v1/recycle-bin/page")
    public Result<Page<LinkPageRespDto>> pageRecycleBin(@SpringQueryMap RecycleBinPageReqDto requestParam) {
        return recycleBinService.pageRecycleBin(requestParam);
    }

    @PostMapping("/api/short-link/admin/v1/recycle-bin/recover")
    public Result<Void> recoverRecycleBinLink(@RequestBody RecycleBinRecoverReqDto requestParam){
        linkActualRemoteService.recoverRecycleBinLink(requestParam);
        return Results.success();
    }

    @PostMapping("/api/short-link/admin/v1/recycle-bin/remove")
    public Result<Void> removeRecycleBinLink(@RequestBody RecycleBinRemoveReqDto requestParam){
        linkActualRemoteService.removeRecycleBinLink(requestParam);
        return Results.success();
    }
    
    
}
