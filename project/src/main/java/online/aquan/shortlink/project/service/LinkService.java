package online.aquan.shortlink.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import online.aquan.shortlink.project.dao.entity.LinkDo;
import online.aquan.shortlink.project.dto.rep.LinkCreateReqDto;
import online.aquan.shortlink.project.dto.resp.LinkCreateRespDto;

public interface LinkService extends IService<LinkDo> {
    LinkCreateRespDto createShortLink(LinkCreateReqDto requestParam);
}
