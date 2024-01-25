package online.aquan.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import online.aquan.shortlink.admin.dao.entity.GroupDo;
import online.aquan.shortlink.admin.dto.req.GroupSaveDto;
import online.aquan.shortlink.admin.dto.req.GroupSortDto;
import online.aquan.shortlink.admin.dto.req.GroupUpdateDto;
import online.aquan.shortlink.admin.dto.resp.GroupRepsDto;

import java.util.List;

public interface GroupService extends IService<GroupDo> {

    void saveGroup(GroupSaveDto requestParam);

    List<GroupRepsDto> getGroupList();

    void updateGroup(GroupUpdateDto requestParam);

    void deleteGroup(String gid);

    void sortGroup(List<GroupSortDto> requestParam);

    void saveGroup(String username, String groupName);
}
