package online.aquan.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import online.aquan.shortlink.project.dao.entity.LinkDo;
import online.aquan.shortlink.project.dto.rep.LinkCreateReqDto;
import online.aquan.shortlink.project.dto.rep.LinkPageReqDto;
import online.aquan.shortlink.project.dto.resp.LinkCreateRespDto;
import online.aquan.shortlink.project.dto.resp.LinkPageRespDto;

public interface LinkService extends IService<LinkDo> {
    LinkCreateRespDto createShortLink(LinkCreateReqDto requestParam);

    IPage<LinkPageRespDto> pageShortLink(LinkPageReqDto requestParam);
}
