package online.aquan.shortlink.project.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import online.aquan.shortlink.project.common.convention.result.Result;
import online.aquan.shortlink.project.common.convention.result.Results;
import online.aquan.shortlink.project.dto.rep.LinkCreateReqDto;
import online.aquan.shortlink.project.dto.rep.LinkPageReqDto;
import online.aquan.shortlink.project.dto.resp.LinkCreateRespDto;
import online.aquan.shortlink.project.dto.resp.LinkPageRespDto;
import online.aquan.shortlink.project.service.LinkService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LinkController {
    
    private final LinkService linkService;

    /**
     * 创建短链接
     */
    @PostMapping("/api/short-link/admin/v1/create")
    public Result<LinkCreateRespDto> createShortLink(@RequestBody LinkCreateReqDto requestParam){
        return Results.success(linkService.createShortLink(requestParam));
    }

    /**
     * 分页返回短链接
     * @param requestParam gid
     * @return 短链接集合
     */
    @GetMapping("/api/short-link/admin/v1/page")
    public Result<IPage<LinkPageRespDto>> pageShortLink( LinkPageReqDto requestParam){
        return Results.success(linkService.pageShortLink(requestParam));
    }
}
