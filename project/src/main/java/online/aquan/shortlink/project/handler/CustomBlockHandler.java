package online.aquan.shortlink.project.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import online.aquan.shortlink.project.common.convention.result.Result;
import online.aquan.shortlink.project.dto.req.LinkCreateReqDto;
import online.aquan.shortlink.project.dto.resp.LinkCreateRespDto;

public class CustomBlockHandler {
    public static Result<LinkCreateRespDto> createShortLinkBlockHandlerMethod(LinkCreateReqDto requestParam, BlockException exception) {
        return new Result<LinkCreateRespDto>().setCode("B100000").setMessage("当前访问网站人数过多，请稍后再试...");
    }
}
