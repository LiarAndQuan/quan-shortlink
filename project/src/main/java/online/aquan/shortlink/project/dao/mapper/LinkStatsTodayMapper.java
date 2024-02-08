package online.aquan.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.aquan.shortlink.project.dao.entity.LinkStatsTodayDo;
import org.apache.ibatis.annotations.Update;


public interface LinkStatsTodayMapper extends BaseMapper<LinkStatsTodayDo> {

    @Update(
            """
                        insert into t_link_stats_today_0(gid, full_short_url, date, today_pv, today_uv, today_uip, create_time, update_time, del_flag)
                        values (#{gid},#{fullShortUrl},#{date},#{todayPv},#{todayUv},#{todayUip},now(),now(),0)
                        on duplicate key update today_pv=today_pv+#{todayPv},today_uv = today_uv+#{todayUv},today_uip = today_uip+#{todayUip}
                    """
    )
    void statsToday(LinkStatsTodayDo linkStatsTodayDo);
}
