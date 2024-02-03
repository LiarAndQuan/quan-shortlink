package online.aquan.shortlink.admin.controller;


import online.aquan.shortlink.admin.common.convention.result.Result;
import online.aquan.shortlink.admin.remote.dto.LinkRemoteService;
import online.aquan.shortlink.admin.remote.dto.req.RecycleBinCreateReqDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecycleBinController {

    private final LinkRemoteService linkRemoteService = new LinkRemoteService() {
    };

    @PostMapping("/api/short-link/admin/v1/recycle-bin/save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinCreateReqDto requestParam) {
        return linkRemoteService.saveRecycleBin(requestParam);
    }

}
