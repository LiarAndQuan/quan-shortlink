package online.aquan.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.aquan.shortlink.project.dao.entity.LinkBrowserStatsDo;
import online.aquan.shortlink.project.dto.req.LinkStatsGroupReqDto;
import online.aquan.shortlink.project.dto.req.LinkStatsReqDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LinkBrowserStatsMapper extends BaseMapper<LinkBrowserStatsDo> {

    @Insert("""
            insert into t_link_browser_stats(full_short_url, gid, date, cnt, browser, create_time, update_time, del_flag)
             VALUES (#{fullShortUrl},#{gid},#{date},#{cnt},#{browser},#{createTime},#{updateTime},0) 
             On duplicate key update cnt = cnt +1,update_time = #{date};
            """)
    void insertOrUpdate(LinkBrowserStatsDo linkBrowserStatsDo);

    @Select(
            """
                                    select browser,sum(cnt) as cnt from t_link_browser_stats
                                    where gid = #{gid} and full_short_url=#{fullShortUrl} 
                                                and date between #{startDate} and #{endDate}
                                    group by gid,full_short_url,browser
                    """
    )
    List<LinkBrowserStatsDo> getBrowserAndCnt(LinkStatsReqDto requestParam);


    @Select(
            """
                                    select browser,sum(cnt) as cnt from t_link_browser_stats
                                    where gid = #{gid}
                                                and date between #{startDate} and #{endDate}
                                    group by gid,browser
                    """
    )
    List<LinkBrowserStatsDo> getGroupBrowserAndCnt(LinkStatsGroupReqDto requestParam);
}
