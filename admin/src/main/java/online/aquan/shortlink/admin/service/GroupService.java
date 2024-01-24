package online.aquan.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import online.aquan.shortlink.admin.dao.entity.GroupDo;
import online.aquan.shortlink.admin.dto.req.GroupSaveDto;

public interface GroupService extends IService<GroupDo> {

    void saveGroup(GroupSaveDto requestParam);
}
