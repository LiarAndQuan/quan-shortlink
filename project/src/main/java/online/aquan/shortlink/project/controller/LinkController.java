package online.aquan.shortlink.project.controller;


import lombok.RequiredArgsConstructor;
import online.aquan.shortlink.project.common.convention.result.Result;
import online.aquan.shortlink.project.common.convention.result.Results;
import online.aquan.shortlink.project.dto.rep.LinkCreateReqDto;
import online.aquan.shortlink.project.dto.resp.LinkCreateRespDto;
import online.aquan.shortlink.project.service.LinkService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LinkController {
    
    private final LinkService linkService;
    
    @PostMapping("/api/short-link/admin/v1/create")
    public Result<LinkCreateRespDto> createShortLink(@RequestBody LinkCreateReqDto requestParam){
        return Results.success(linkService.createShortLink(requestParam));
    }
}
