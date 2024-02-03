package online.aquan.shortlink.project.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import online.aquan.shortlink.project.common.convention.result.Result;
import online.aquan.shortlink.project.common.convention.result.Results;
import online.aquan.shortlink.project.dto.req.RecycleBinCreateReqDto;
import online.aquan.shortlink.project.dto.req.RecycleBinPageReqDto;
import online.aquan.shortlink.project.dto.req.RecycleBinRecoverReqDto;
import online.aquan.shortlink.project.dto.resp.LinkPageRespDto;
import online.aquan.shortlink.project.service.RecycleBinService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RecycleBinController {

    private final RecycleBinService recycleBinService;

    @PostMapping("/api/short-link/v1/recycle-bin/save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinCreateReqDto requestParam) {
        recycleBinService.saveRecycleBin(requestParam);
        return Results.success();
    }

    @GetMapping("/api/short-link/v1/recycle-bin/page")
    public Result<IPage<LinkPageRespDto>> pageRecycleBin(RecycleBinPageReqDto requestParam) {
        return Results.success(recycleBinService.pageRecycleBinShortLink(requestParam));
    }
    
    @PostMapping("/api/short-link/v1/recycle-bin/recover")
    public Result<Void> recoverRecycleBinLink(@RequestBody RecycleBinRecoverReqDto requestParam){
        recycleBinService.recoverRecycleBinLink(requestParam);
        return Results.success();
    }

}
