package online.aquan.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.aquan.shortlink.project.dao.entity.LinkOsStatsDo;
import org.apache.ibatis.annotations.Insert;


public interface LinkOsStatsMapper extends BaseMapper<LinkOsStatsDo> {
    @Insert("""
            insert into t_link_os_stats(full_short_url, gid, date, cnt, os, create_time, update_time, del_flag)
             VALUES (#{fullShortUrl},#{gid},#{date},#{cnt},#{os},#{createTime},#{updateTime},0) 
             On duplicate key update cnt = cnt +1,update_time = #{date};
            """)
    void insertOrUpdate(LinkOsStatsDo linkOsStatsDo);
}
