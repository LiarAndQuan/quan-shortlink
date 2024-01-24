package online.aquan.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.aquan.shortlink.admin.common.biz.user.UserContext;
import online.aquan.shortlink.admin.dao.entity.GroupDo;
import online.aquan.shortlink.admin.dao.mapper.GroupMapper;
import online.aquan.shortlink.admin.dto.req.GroupSaveDto;
import online.aquan.shortlink.admin.dto.req.GroupUpdateDto;
import online.aquan.shortlink.admin.dto.resp.GroupRepsDto;
import online.aquan.shortlink.admin.service.GroupService;
import online.aquan.shortlink.admin.toolkit.RandomGenerator;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDo> implements GroupService {

    /**
     * 新增短链接的分组
     */
    @Override
    public void saveGroup(GroupSaveDto requestParam) {
        String gid;
        //生成一个数据库中没有的gid
        while (true) {
            gid = RandomGenerator.generateRandom();
            LambdaQueryWrapper<GroupDo> wrapper = Wrappers.lambdaQuery(GroupDo.class)
                    .eq(GroupDo::getGid, gid)
                    .eq(GroupDo::getUsername, UserContext.getUsername());
            GroupDo groupDo = baseMapper.selectOne(wrapper);
            if (groupDo == null) {
                break;
            }
        }
        //插入数据
        GroupDo groupDo = GroupDo.builder()
                .gid(gid)
                .name(requestParam.getName())
                .username(UserContext.getUsername())
                .sortOrder(0).build();
        baseMapper.insert(groupDo);
    }

    /**
     * 获取短链接的分组
     *
     * @return 分组集合
     */
    @Override
    public List<GroupRepsDto> getGroupList() {
        //noinspection unchecked
        LambdaQueryWrapper<GroupDo> wrapper = Wrappers.lambdaQuery(GroupDo.class)
                .eq(GroupDo::getUsername, UserContext.getUsername())
                .eq(GroupDo::getDelFlag, 0)
                //降序排序
                .orderByDesc(GroupDo::getSortOrder, GroupDo::getUpdateTime);
        List<GroupDo> groupDos = baseMapper.selectList(wrapper);
        return BeanUtil.copyToList(groupDos, GroupRepsDto.class);
    }

    /**
     * 修改短链接
     * @param requestParam gid,new name
     */
    @Override
    public void update(GroupUpdateDto requestParam) {
        LambdaUpdateWrapper<GroupDo> wrapper = Wrappers.lambdaUpdate(GroupDo.class)
                .eq(GroupDo::getGid, requestParam.getGid())
                .eq(GroupDo::getDelFlag,0);
        GroupDo groupDo = GroupDo.builder()
                .gid(requestParam.getGid())
                .name(requestParam.getName())
                .build();
        baseMapper.update(groupDo,wrapper);
    }
}
