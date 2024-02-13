package online.aquan.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import online.aquan.shortlink.project.dao.entity.LinkDo;
import online.aquan.shortlink.project.dto.req.LinkBatchCreateReqDto;
import online.aquan.shortlink.project.dto.req.LinkCreateReqDto;
import online.aquan.shortlink.project.dto.req.LinkPageReqDto;
import online.aquan.shortlink.project.dto.req.LinkUpdateReqDto;
import online.aquan.shortlink.project.dto.resp.LinkBatchCreateRespDto;
import online.aquan.shortlink.project.dto.resp.LinkCreateRespDto;
import online.aquan.shortlink.project.dto.resp.LinkGroupCountRespDto;
import online.aquan.shortlink.project.dto.resp.LinkPageRespDto;

import java.util.List;

public interface LinkService extends IService<LinkDo> {
    LinkCreateRespDto createShortLink(LinkCreateReqDto requestParam);

    IPage<LinkPageRespDto> pageShortLink(LinkPageReqDto requestParam);

    List<LinkGroupCountRespDto> getLinkGroupCount(List<String> requestParam);

    void updateLink(LinkUpdateReqDto requestParam);

    void restoreLink(String shortUrl, ServletRequest servletRequest, ServletResponse servletResponse);

    LinkBatchCreateRespDto batchCreateShortLink(LinkBatchCreateReqDto requestParam);
}
