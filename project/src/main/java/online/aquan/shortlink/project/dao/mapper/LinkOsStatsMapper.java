package online.aquan.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.aquan.shortlink.project.dao.entity.LinkOsStatsDo;
import online.aquan.shortlink.project.dto.req.LinkStatsGroupReqDto;
import online.aquan.shortlink.project.dto.req.LinkStatsReqDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface LinkOsStatsMapper extends BaseMapper<LinkOsStatsDo> {
    @Insert("""
            insert into t_link_os_stats(full_short_url, gid, date, cnt, os, create_time, update_time, del_flag)
             VALUES (#{fullShortUrl},#{gid},#{date},#{cnt},#{os},#{createTime},#{updateTime},0) 
             On duplicate key update cnt = cnt +1,update_time = #{date};
            """)
    void insertOrUpdate(LinkOsStatsDo linkOsStatsDo);

    @Select(
            """
                                    select os,sum(cnt) as cnt from t_link_os_stats
                                    where gid = #{gid} and full_short_url=#{fullShortUrl} 
                                                and date between #{startDate} and #{endDate}
                                    group by gid,full_short_url,os
                    """
    )
    List<LinkOsStatsDo> getOsAndCnt(LinkStatsReqDto requestParam);

    @Select(
            """
                                    select os,sum(cnt) as cnt from t_link_os_stats
                                    where gid = #{gid} 
                                                and date between #{startDate} and #{endDate}
                                    group by gid,os
                    """
    )
    List<LinkOsStatsDo> getGroupOsAndCnt(LinkStatsGroupReqDto requestParam);
}
