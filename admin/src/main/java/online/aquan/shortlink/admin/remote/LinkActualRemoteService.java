package online.aquan.shortlink.admin.remote;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.aquan.shortlink.admin.common.convention.result.Result;
import online.aquan.shortlink.admin.dto.req.LinkStatsReqDto;
import online.aquan.shortlink.admin.remote.dto.req.*;
import online.aquan.shortlink.admin.remote.dto.resp.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "short-link-project", url = "${aggregation.remote-url:}")
@Service
public interface LinkActualRemoteService {

    @PostMapping("/api/short-link/v1/create")
    Result<LinkCreateRespDto> createLink(@RequestBody LinkCreateReqDto requestParam);

    @PostMapping("/api/short-link/v1/create/batch")
    Result<LinkBatchCreateRespDto> batchCreateShortLink(@RequestBody LinkBatchCreateReqDto requestParam);

    //@SpringQueryMap可以让feign使用get方式传输参数
    @GetMapping("/api/short-link/v1/page")
    Result<Page<LinkPageRespDto>> pageLink(@SpringQueryMap LinkPageReqDto requestParam);

    @PostMapping("/api/short-link/v1/update")
    Result<Void> updateLink(@RequestBody LinkUpdateReqDto requestParam);

    @GetMapping("/api/short-link/v1/count")
    Result<List<LinkGroupCountRespDto>> getGroupLinkCount(@RequestParam("requestParam") List<String> requestParam);

    @GetMapping("/api/short-link/v1/title")
    Result<String> getTitleByUrl(@RequestParam("url") String url);

    @PostMapping("/api/short-link/v1/recycle-bin/save")
    Result<Void> saveRecycleBin(@RequestBody RecycleBinCreateReqDto requestParam);

    @GetMapping("/api/short-link/v1/recycle-bin/page")
    Result<Page<LinkPageRespDto>> pageRecycleBinShortLink(@SpringQueryMap RecycleBinPageReqDto requestParam);

    @PostMapping("/api/short-link/v1/recycle-bin/recover")
    void recoverRecycleBinLink(@RequestBody RecycleBinRecoverReqDto requestParam);

    @PostMapping("/api/short-link/v1/recycle-bin/remove")
    void removeRecycleBinLink(@RequestBody RecycleBinRemoveReqDto requestParam);

    @GetMapping("/api/short-link/v1/stats")
    Result<LinkStatsRespDto> getLinkStats(@SpringQueryMap LinkStatsReqDto requestParam);

    @GetMapping("/api/short-link/v1/stats/access-record")
    Result<Page<LinkAccessRecordsPageRepsDto>> linkPageAccessRecords(@SpringQueryMap LinkAccessRecordsPageReqDto requestParam);

    @GetMapping("/api/short-link/v1/stats/group")
    Result<LinkStatsRespDto> getGroupLinkStats(@SpringQueryMap LinkStatsGroupReqDto requestParam);

    @GetMapping("/api/short-link/v1/stats/access-record/group")
    Result<Page<LinkAccessRecordsPageRepsDto>> linkGroupPageAccessRecords(@SpringQueryMap LinkAccessRecordsPageGroupReqDto requestParam);
}
