package online.aquan.shortlink.project.controller;

import lombok.RequiredArgsConstructor;
import online.aquan.shortlink.project.common.convention.result.Result;
import online.aquan.shortlink.project.common.convention.result.Results;
import online.aquan.shortlink.project.service.UrlTitleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UrlTitleController {
    
    private final UrlTitleService urlTitleService;

    /**
     * 获取网站的标题
     * @param url 网站url
     * @return 网站标题
     */
    @GetMapping("/api/short-link/v1/title")
    public Result<String> getTitleByUrl(@RequestParam("url") String url){
        return Results.success(urlTitleService.getTitleByUrl(url));
    }
    
}
