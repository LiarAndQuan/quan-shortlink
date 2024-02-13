package online.aquan.shortlink.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.servlet.http.HttpServletResponse;
import online.aquan.shortlink.admin.common.convention.result.Result;
import online.aquan.shortlink.admin.remote.dto.LinkRemoteService;
import online.aquan.shortlink.admin.remote.dto.req.LinkBatchCreateReqDto;
import online.aquan.shortlink.admin.remote.dto.req.LinkCreateReqDto;
import online.aquan.shortlink.admin.remote.dto.req.LinkPageReqDto;
import online.aquan.shortlink.admin.remote.dto.req.LinkUpdateReqDto;
import online.aquan.shortlink.admin.remote.dto.resp.*;
import online.aquan.shortlink.admin.toolkit.EasyExcelWebUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后管调用中台的服务
 * todo 暂时使用http调用,将来重构成为SpringCloud调用
 */
@RestController
public class LinkController {

    /**
     * springboot不能管理没有任何实现类的接口
     */
    private final LinkRemoteService linkRemoteService = new LinkRemoteService() {
    };

    @PostMapping("/api/short-link/admin/v1/create")
    public Result<LinkCreateRespDto> createLink(@RequestBody LinkCreateReqDto requestParam) {
        return linkRemoteService.createLink(requestParam);
    }

    @PostMapping("/api/short-link/admin/v1/create/batch")
    public void batchCreateShortLink(@RequestBody LinkBatchCreateReqDto requestParam, HttpServletResponse response) {
        Result<LinkBatchCreateRespDto> result = linkRemoteService.batchCreateShortLink(requestParam);
        if (result.isSuccess()) {
            List<LinkBaseInfoRespDto> baseLinkInfos = result.getData().getBaseLinkInfos();
            EasyExcelWebUtil.write(response, "批量创建短链接", LinkBaseInfoRespDto.class, baseLinkInfos);
        }
    }

    @GetMapping("/api/short-link/admin/v1/page")
    public Result<IPage<LinkPageRespDto>> pageShortLink(LinkPageReqDto requestParam) {
        return linkRemoteService.pageLink(requestParam);
    }

    @GetMapping("/api/short-link/admin/v1/count")
    public Result<List<LinkGroupCountRespDto>> getGroupLinkCount(@RequestParam("requestParam") List<String> requestParam) {
        return linkRemoteService.getGroupLinkCount(requestParam);
    }

    /**
     * 修改短链接
     */
    @PostMapping("/api/short-link/admin/v1/update")
    public Result<Void> updateLink(@RequestBody LinkUpdateReqDto requestParam) {
        return linkRemoteService.updateLink(requestParam);
    }

}
