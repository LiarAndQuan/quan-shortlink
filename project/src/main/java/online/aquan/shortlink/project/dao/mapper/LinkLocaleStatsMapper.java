package online.aquan.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.aquan.shortlink.project.dao.entity.LinkLocaleStatsDo;
import online.aquan.shortlink.project.dto.req.LinkStatsGroupReqDto;
import online.aquan.shortlink.project.dto.req.LinkStatsReqDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface LinkLocaleStatsMapper extends BaseMapper<LinkLocaleStatsDo> {


    @Insert("""
            insert into t_link_locale_stats(full_short_url, gid, date, cnt, province, city, adcode, country, create_time, update_time, del_flag)
             VALUES (#{fullShortUrl},#{gid},#{date},#{cnt},#{province},#{city},#{adcode},#{country},#{createTime},#{updateTime},0) 
             On duplicate key update cnt = cnt +1,update_time = #{date};
            """)
    void insertOrUpdate(LinkLocaleStatsDo localeStatsDo);


    /**
     * 统计时间段内的省的访问数
     * @return province,cnt
     */
    @Select(
            """
                    select province,sum(cnt) as cnt from t_link_locale_stats
                    where gid = #{gid} and full_short_url=#{fullShortUrl} 
                    and date between #{startDate} and #{endDate}
                    group by gid,full_short_url, province;
                    """
    )
    List<LinkLocaleStatsDo> getLocaleAndCnt(LinkStatsReqDto requestParam);

    @Select(
            """
                    select province,sum(cnt) as cnt from t_link_locale_stats
                    where gid = #{gid} 
                    and date between #{startDate} and #{endDate}
                    group by gid, province;
                    """
    )
    List<LinkLocaleStatsDo> getGroupLocaleAndCnt(LinkStatsGroupReqDto requestParam);
}
