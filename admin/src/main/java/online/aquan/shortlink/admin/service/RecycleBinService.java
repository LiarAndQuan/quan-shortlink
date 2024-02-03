package online.aquan.shortlink.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import online.aquan.shortlink.admin.common.convention.result.Result;
import online.aquan.shortlink.admin.remote.dto.req.RecycleBinPageReqDto;
import online.aquan.shortlink.admin.remote.dto.resp.LinkPageRespDto;

public interface RecycleBinService  {
    Result<IPage<LinkPageRespDto>> pageRecycleBin(RecycleBinPageReqDto requestParam);
}
