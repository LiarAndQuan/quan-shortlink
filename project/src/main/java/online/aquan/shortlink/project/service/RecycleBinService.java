package online.aquan.shortlink.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import online.aquan.shortlink.project.dao.entity.LinkDo;
import online.aquan.shortlink.project.dto.rep.RecycleBinCreateReqDto;

public interface RecycleBinService extends IService<LinkDo> {
    

    void saveRecycleBin(RecycleBinCreateReqDto requestParam);

}
