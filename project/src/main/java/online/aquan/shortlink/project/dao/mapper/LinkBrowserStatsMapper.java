package online.aquan.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.aquan.shortlink.project.dao.entity.LinkBrowserStatsDo;
import org.apache.ibatis.annotations.Insert;

public interface LinkBrowserStatsMapper extends BaseMapper<LinkBrowserStatsDo> {

    @Insert("""
            insert into t_link_browser_stats(full_short_url, gid, date, cnt, browser, create_time, update_time, del_flag)
             VALUES (#{fullShortUrl},#{gid},#{date},#{cnt},#{browser},#{createTime},#{updateTime},0) 
             On duplicate key update cnt = cnt +1,update_time = #{date};
            """)
    void insertOrUpdate(LinkBrowserStatsDo linkBrowserStatsDo);
}
