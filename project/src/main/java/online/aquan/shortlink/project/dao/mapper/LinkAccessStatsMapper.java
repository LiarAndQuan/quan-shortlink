package online.aquan.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.aquan.shortlink.project.dao.entity.LinkAccessStatsDo;
import online.aquan.shortlink.project.dto.req.LinkStatsReqDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LinkAccessStatsMapper extends BaseMapper<LinkAccessStatsDo> {

    //自动注入只对自动生成的sql有效
    @Insert("""
            insert into t_link_access_stats(gid, full_short_url, date, pv, uv, uip, hour, weekday,t_link_access_stats.create_time,t_link_access_stats.update_time,del_flag)
             VALUES (#{gid},#{fullShortUrl},#{date},#{pv},#{uv},#{uip},#{hour},#{weekday},#{createTime},#{updateTime},0) 
             On duplicate key update pv = pv+#{pv},uv = uv+#{uv},uip = uip+#{uip},update_time=#{updateTime}
            """)
    void insertOrUpdate(LinkAccessStatsDo linkAccessStatsDo);

    /**
     * 返回每一天的基础数据pv,uv,uip
     *
     * @return date, pv, uv, uip
     */
    @Select(
            """
                    select date, sum(uv) as uv,sum(pv) as pv,sum(uip) as uip from t_link_access_stats
                    where gid = #{gid} and full_short_url=#{fullShortUrl} 
                    and date between #{startDate} and #{endDate}
                    group by gid,full_short_url,date; 
           """
    )
    List<LinkAccessStatsDo> getDailyLinkStats(LinkStatsReqDto requestParam);


    /**
     * 统计这段时间之内的各个小时段访问量之和
     *
     * @return 各个小时访问量之和
     */
    @Select(
            """
                                    select hour,sum(pv) as pv from t_link_access_stats
                                    where gid = #{gid} and full_short_url=#{fullShortUrl} 
                                                and date between #{startDate} and #{endDate}
                                    group by gid,full_short_url,hour
                    """
    )
    List<LinkAccessStatsDo> getHoursAndCnt(LinkStatsReqDto requestParam);


    /**
     * 统计一周内每一天访问量之和
     * @return 每一天访问量之和
     */
    @Select(
            """
                                    select weekday,sum(pv) as pv from t_link_access_stats
                                    where gid = #{gid} and full_short_url=#{fullShortUrl} 
                                                and date between #{startDate} and #{endDate}
                                    group by gid,full_short_url,weekday
                    """
    )
    List<LinkAccessStatsDo> getWeekdayCnt(LinkStatsReqDto requestParam);
}
