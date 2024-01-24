package online.aquan.shortlink.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.aquan.shortlink.admin.dao.entity.GroupDo;
import online.aquan.shortlink.admin.dao.mapper.GroupMapper;
import online.aquan.shortlink.admin.dto.req.GroupSaveDto;
import online.aquan.shortlink.admin.service.GroupService;
import online.aquan.shortlink.admin.toolkit.RandomGenerator;
import org.springframework.stereotype.Service;


@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDo> implements GroupService {

    /**
     * 新增短链接的分组
     */
    @Override
    public void saveGroup(GroupSaveDto requestParam) {
        String gid;
        //生成一个数据库中没有的gid
        while(true){
            gid = RandomGenerator.generateRandom();
            LambdaQueryWrapper<GroupDo> wrapper = Wrappers.lambdaQuery(GroupDo.class)
                    .eq(GroupDo::getGid, gid)
                    //todo 这里的用户名等到以后封装用户上下文去填充
                    .eq(GroupDo::getUsername,null);
            GroupDo groupDo = baseMapper.selectOne(wrapper);
            if(groupDo==null){
                break;
            }
        }
        GroupDo groupDo = GroupDo.builder()
                .gid(gid)
                .name(requestParam.getName())
                .sortOrder(0).build();
        baseMapper.insert(groupDo);
    }
}
