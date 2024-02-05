package online.aquan.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.aquan.shortlink.project.dao.entity.LinkLocaleStatsDo;
import org.apache.ibatis.annotations.Insert;


public interface LinkLocaleStatsMapper extends BaseMapper<LinkLocaleStatsDo> {


    @Insert("""
            insert into t_link_locale_stats(full_short_url, gid, date, cnt, province, city, adcode, country, create_time, update_time, del_flag)
             VALUES (#{fullShortUrl},#{gid},#{date},#{cnt},#{province},#{city},#{adcode},#{country},#{createTime},#{updateTime},0) 
             On duplicate key update cnt = cnt +1,update_time = #{date};
            """)
    void insertOrUpdate(LinkLocaleStatsDo localeStatsDo);


}
