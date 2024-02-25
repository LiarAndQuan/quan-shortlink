package online.aquan.shortlink.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import online.aquan.shortlink.admin.common.biz.user.UserContext;
import online.aquan.shortlink.admin.common.convention.exception.ClientException;
import online.aquan.shortlink.admin.common.convention.result.Result;
import online.aquan.shortlink.admin.dao.entity.GroupDo;
import online.aquan.shortlink.admin.dao.mapper.GroupMapper;
import online.aquan.shortlink.admin.remote.LinkActualRemoteService;
import online.aquan.shortlink.admin.remote.dto.req.RecycleBinPageReqDto;
import online.aquan.shortlink.admin.remote.dto.resp.LinkPageRespDto;
import online.aquan.shortlink.admin.service.RecycleBinService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "recycleBinServiceImplByAdmin")
@RequiredArgsConstructor
public class RecycleBinServiceImpl implements RecycleBinService {

    private final GroupMapper groupMapper;
    private final LinkActualRemoteService linkActualRemoteService;

    @Override
    public Result<Page<LinkPageRespDto>> pageRecycleBin(RecycleBinPageReqDto requestParam) {
        //首先获取用户所有的gid,因为这样才可以查询用户的短链接
        LambdaQueryWrapper<GroupDo> wrapper = Wrappers.lambdaQuery(GroupDo.class)
                .eq(GroupDo::getUsername, UserContext.getUsername());
        List<GroupDo> groupDos = groupMapper.selectList(wrapper);
        if (CollUtil.isEmpty(groupDos)) {
            throw new ClientException("该用户没有短链接分组信息");
        }
        List<String> gidList = groupDos.stream().map(GroupDo::getGid).toList();
        requestParam.setGidList(gidList);
        return linkActualRemoteService.pageRecycleBinShortLink(requestParam);
    }
}
