package online.aquan.shortlink.admin.controller;


import online.aquan.shortlink.admin.common.convention.result.Result;
import online.aquan.shortlink.admin.remote.dto.LinkRemoteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlTitleController {

    private final LinkRemoteService linkRemoteService = new LinkRemoteService() {
    };

    /**
     * 获取网站的标题
     *
     * @param url 网站url
     * @return 网站标题
     */
    @GetMapping("/api/short-link/admin/v1/title")
    public Result<String> getTitleByUrl(@RequestParam("url") String url) {
        return linkRemoteService.getTitleByUrl(url);
    }
}
