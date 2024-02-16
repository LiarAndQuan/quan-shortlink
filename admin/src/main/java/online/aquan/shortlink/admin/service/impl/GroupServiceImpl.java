package online.aquan.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import online.aquan.shortlink.admin.common.biz.user.UserContext;
import online.aquan.shortlink.admin.common.constant.RedisCacheConstant;
import online.aquan.shortlink.admin.common.convention.exception.ClientException;
import online.aquan.shortlink.admin.common.convention.result.Result;
import online.aquan.shortlink.admin.dao.entity.GroupDo;
import online.aquan.shortlink.admin.dao.mapper.GroupMapper;
import online.aquan.shortlink.admin.dto.req.GroupSaveDto;
import online.aquan.shortlink.admin.dto.req.GroupSortDto;
import online.aquan.shortlink.admin.dto.req.GroupUpdateDto;
import online.aquan.shortlink.admin.dto.resp.GroupRepsDto;
import online.aquan.shortlink.admin.remote.dto.LinkRemoteService;
import online.aquan.shortlink.admin.remote.dto.resp.LinkGroupCountRespDto;
import online.aquan.shortlink.admin.service.GroupService;
import online.aquan.shortlink.admin.toolkit.RandomGenerator;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDo> implements GroupService {

    private final LinkRemoteService linkRemoteService = new LinkRemoteService() {
    };
    private final RedissonClient redissonClient;
    @Value("${link.group.max-num}")
    private Integer maxGroupNum;

    /**
     * 用户自己新增短链接的分组
     */
    @Override
    public void saveGroup(GroupSaveDto requestParam) {
        saveGroup(UserContext.getUsername(), requestParam.getName());
    }

    /**
     * 新增短链接的分组,包含默认分组
     */
    @Override
    public void saveGroup(String username, String groupName) {
        RLock lock = redissonClient.getLock(RedisCacheConstant.LOCK_GROUP_CREATE_KEY + username);
        lock.lock();
        try {
            LambdaQueryWrapper<GroupDo> wrapper = Wrappers.lambdaQuery(GroupDo.class)
                    .eq(GroupDo::getUsername, username);
            List<GroupDo> groupDos = baseMapper.selectList(wrapper);
            if (CollUtil.isNotEmpty(groupDos) && groupDos.size() == maxGroupNum) {
                throw new ClientException("已超出最大分组数: " + maxGroupNum);
            }
            String gid;
            //生成一个数据库中没有的gid
            do {
                gid = RandomGenerator.generateRandom();
            } while (!hasGid(username, gid));
            //插入数据
            GroupDo groupDo = GroupDo.builder()
                    .gid(gid)
                    .name(groupName)
                    .username(username)
                    .sortOrder(0).build();
            baseMapper.insert(groupDo);
        } finally {
            lock.unlock();
        }
       
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
        //获取所有的分组
        List<GroupDo> groupDos = baseMapper.selectList(wrapper);
        //调用中台获取所有的gid的linkCount
        Result<List<LinkGroupCountRespDto>> groupLinkCount = linkRemoteService
                .getGroupLinkCount(groupDos.stream().map(GroupDo::getGid).toList());
        List<GroupRepsDto> groupRepsDtos = BeanUtil.copyToList(groupDos, GroupRepsDto.class);
        //将所有的数量赋值给对应的gid即可
        Map<String, Integer> map = new HashMap<>();
        groupLinkCount.getData().forEach(
                (item) -> {
                    map.put(item.getGid(), item.getShortLinkCount());
                }
        );
        groupRepsDtos.forEach(
                (item) -> {
                    item.setShortLinkCount(map.get(item.getGid()));
                }
        );
        return groupRepsDtos;
    }

    /**
     * 修改短链接
     *
     * @param requestParam gid,new name
     */
    @Override
    public void updateGroup(GroupUpdateDto requestParam) {
        LambdaUpdateWrapper<GroupDo> wrapper = Wrappers.lambdaUpdate(GroupDo.class)
                .eq(GroupDo::getGid, requestParam.getGid())
                .eq(GroupDo::getUsername, UserContext.getUsername())
                .eq(GroupDo::getDelFlag, 0);
        GroupDo groupDo = GroupDo.builder()
                .gid(requestParam.getGid())
                .name(requestParam.getName())
                .build();
        baseMapper.update(groupDo, wrapper);
    }

    /**
     * 删除短链接
     *
     * @param gid gid
     */
    @Override
    public void deleteGroup(String gid) {
        LambdaUpdateWrapper<GroupDo> wrapper = Wrappers.lambdaUpdate(GroupDo.class)
                .eq(GroupDo::getDelFlag, 0)
                .eq(GroupDo::getGid, gid)
                .eq(GroupDo::getUsername, UserContext.getUsername());
        GroupDo groupDo = new GroupDo();
        groupDo.setDelFlag(1);
        baseMapper.update(groupDo, wrapper);
    }

    /**
     * 排序短链接
     *
     * @param requestParam gid,sortOrder
     */
    @Override
    public void sortGroup(List<GroupSortDto> requestParam) {
        requestParam.forEach(
                (item) -> {
                    LambdaUpdateWrapper<GroupDo> wrapper = Wrappers.lambdaUpdate(GroupDo.class)
                            .eq(GroupDo::getGid, item.getGid())
                            .eq(GroupDo::getUsername, UserContext.getUsername())
                            .eq(GroupDo::getDelFlag, 0);
                    GroupDo groupDo = GroupDo.builder()
                            .sortOrder(item.getSortOrder())
                            .build();
                    baseMapper.update(groupDo, wrapper);
                }
        );
    }

    private boolean hasGid(String username, String gid) {
        LambdaQueryWrapper<GroupDo> queryWrapper = Wrappers.lambdaQuery(GroupDo.class)
                .eq(GroupDo::getGid, gid)
//                .eq(GroupDo::getUsername, Optional.ofNullable(username).orElse(UserContext.getUsername()));
                .eq(GroupDo::getUsername, username);
        GroupDo hasGroupFlag = baseMapper.selectOne(queryWrapper);
        return hasGroupFlag == null;
    }
}
