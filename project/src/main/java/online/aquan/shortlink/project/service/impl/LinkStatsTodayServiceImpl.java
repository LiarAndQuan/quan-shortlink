package online.aquan.shortlink.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.aquan.shortlink.project.dao.entity.LinkStatsTodayDo;
import online.aquan.shortlink.project.dao.mapper.LinkStatsTodayMapper;
import online.aquan.shortlink.project.service.LinkStatsTodayService;
import org.springframework.stereotype.Service;

@Service
public class LinkStatsTodayServiceImpl extends ServiceImpl<LinkStatsTodayMapper, LinkStatsTodayDo> implements LinkStatsTodayService {
    
}
