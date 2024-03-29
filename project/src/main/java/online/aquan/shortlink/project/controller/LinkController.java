package online.aquan.shortlink.project.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.RequiredArgsConstructor;
import online.aquan.shortlink.project.common.convention.result.Result;
import online.aquan.shortlink.project.common.convention.result.Results;
import online.aquan.shortlink.project.dto.req.LinkBatchCreateReqDto;
import online.aquan.shortlink.project.dto.req.LinkCreateReqDto;
import online.aquan.shortlink.project.dto.req.LinkPageReqDto;
import online.aquan.shortlink.project.dto.req.LinkUpdateReqDto;
import online.aquan.shortlink.project.dto.resp.LinkBatchCreateRespDto;
import online.aquan.shortlink.project.dto.resp.LinkCreateRespDto;
import online.aquan.shortlink.project.dto.resp.LinkGroupCountRespDto;
import online.aquan.shortlink.project.dto.resp.LinkPageRespDto;
import online.aquan.shortlink.project.handler.CustomBlockHandler;
import online.aquan.shortlink.project.service.LinkService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;

    /**
     * 创建短链接
     */
    @PostMapping("/api/short-link/v1/create")
    @SentinelResource(
            value = "create_short-link",
            blockHandler = "createShortLinkBlockHandlerMethod",
            blockHandlerClass = CustomBlockHandler.class
    )
    public Result<LinkCreateRespDto> createShortLink(@RequestBody LinkCreateReqDto requestParam) {
        return Results.success(linkService.createShortLink(requestParam));
    }

    /**
     * 批量创建短链接
     */
    @PostMapping("/api/short-link/v1/create/batch")
    public Result<LinkBatchCreateRespDto> batchCreateShortLink(@RequestBody LinkBatchCreateReqDto requestParam) {
        return Results.success(linkService.batchCreateShortLink(requestParam));
    }

    /**
     * 分页返回短链接
     *
     * @param requestParam gid
     * @return 短链接集合
     */
    @GetMapping("/api/short-link/v1/page")
    public Result<IPage<LinkPageRespDto>> pageShortLink(LinkPageReqDto requestParam) {
        return Results.success(linkService.pageShortLink(requestParam));
    }

    @GetMapping("/api/short-link/v1/count")
    public Result<List<LinkGroupCountRespDto>> getLinkGroupCount(@RequestParam("requestParam") List<String> requestParam) {
        return Results.success(linkService.getLinkGroupCount(requestParam));
    }

    /**
     * 修改短链接
     */
    @PostMapping("/api/short-link/v1/update")
    public Result<Void> updateLink(@RequestBody LinkUpdateReqDto requestParam) {
        linkService.updateLink(requestParam);
        return Results.success();
    }

    @GetMapping("{short_url}")
    public void restoreLink(@PathVariable("short_url") String short_url, ServletRequest servletRequest, ServletResponse servletResponse) {
        linkService.restoreLink(short_url, servletRequest, servletResponse);
    }
}
