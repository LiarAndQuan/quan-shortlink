package online.aquan.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.aquan.shortlink.project.dao.entity.LinkAccessLogsDo;
import online.aquan.shortlink.project.dto.req.LinkStatsReqDto;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

public interface LinkAccessLogsMapper extends BaseMapper<LinkAccessLogsDo> {

    /**
     * 统计这段时间之内的最高访问量的ip
     *
     * @return 最高访问ip及其数量
     */
    @Select(
            """
                                    select ip,count(*) as count from t_link_access_logs
                                    where gid = #{gid} and full_short_url=#{fullShortUrl} 
                                                and create_time 
                                                between concat(#{startDate},' 00:00:00') and concat(#{endDate},' 23:59:59')
                                    group by gid,full_short_url,ip
                                    order by count desc
                                    limit 0,5
                    """
    )
    List<HashMap<String, Object>> getTopIpAndCnt(LinkStatsReqDto requestParam);


    /**
     * 统计是新访客还是老访客,目前sql存在问题,待优化
     *
     * @return 新老访客数量
     */
    @Select(
            """
                        select sum(old_user) as oldUserCount,
                        sum(new_user) as newUserCount
                        from 
                        (select
                        IF(count(distinct(date(create_time)))>1, 1, 0) as 'old_user',
                        IF(count(distinct(date(create_time)))=1 and  max(create_time)>= #{startDate} and max(create_time)<= #{endDate}, 1, 0) as 'new_user'
                        from t_link_access_logs
                        where gid = #{gid} and full_short_url = #{fullShortUrl}
                        group by user) as user_counts
                    """
    )
    HashMap<String, Object> getUvAndCnt(LinkStatsReqDto requestParam);
}
