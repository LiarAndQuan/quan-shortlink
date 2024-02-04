package online.aquan.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.aquan.shortlink.project.dao.entity.LinkAccessStatsDo;
import org.apache.ibatis.annotations.Insert;

public interface LinkAccessStatsMapper extends BaseMapper<LinkAccessStatsDo> {

    //自动注入只对自动生成的sql有效
    @Insert("""
            insert into t_link_access_stats(gid, full_short_url, date, pv, uv, uip, hour, weekday,t_link_access_stats.create_time,t_link_access_stats.update_time,del_flag)
             VALUES (#{gid},#{fullShortUrl},#{date},#{pv},#{uv},#{uip},#{hour},#{weekday},#{createTime},#{updateTime},0) 
             On duplicate key update pv = pv+#{pv},uv = uv+#{uv},uip = uip+#{uip},update_time=#{updateTime}
            """)
    void insertOrUpdate(LinkAccessStatsDo linkAccessStatsDo);
    
}
