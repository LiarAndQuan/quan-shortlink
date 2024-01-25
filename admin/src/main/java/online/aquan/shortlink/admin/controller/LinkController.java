package online.aquan.shortlink.admin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import online.aquan.shortlink.admin.common.convention.result.Result;
import online.aquan.shortlink.admin.remote.dto.LinkRemoteService;
import online.aquan.shortlink.admin.remote.dto.rep.LinkCreateReqDto;
import online.aquan.shortlink.admin.remote.dto.rep.LinkPageReqDto;
import online.aquan.shortlink.admin.remote.dto.resp.LinkCreateRespDto;
import online.aquan.shortlink.admin.remote.dto.resp.LinkPageRespDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


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

    @GetMapping("/api/short-link/admin/v1/page")
    public Result<IPage<LinkPageRespDto>> pageShortLink(LinkPageReqDto requestParam) {
        return linkRemoteService.pageLink(requestParam);
    }

}
