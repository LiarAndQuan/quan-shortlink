package online.aquan.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import online.aquan.shortlink.project.dao.entity.LinkDo;
import online.aquan.shortlink.project.dto.req.RecycleBinCreateReqDto;
import online.aquan.shortlink.project.dto.req.RecycleBinPageReqDto;
import online.aquan.shortlink.project.dto.req.RecycleBinRecoverReqDto;
import online.aquan.shortlink.project.dto.resp.LinkPageRespDto;

public interface RecycleBinService extends IService<LinkDo> {
    

    void saveRecycleBin(RecycleBinCreateReqDto requestParam);

    IPage<LinkPageRespDto> pageRecycleBinShortLink(RecycleBinPageReqDto requestParam);

    void recoverRecycleBinLink(RecycleBinRecoverReqDto requestParam);
}
