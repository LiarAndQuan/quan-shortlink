package online.aquan.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.aquan.shortlink.project.dao.entity.LinkAccessLogsDo;
import online.aquan.shortlink.project.dao.entity.LinkAccessStatsDo;
import online.aquan.shortlink.project.dto.req.LinkStatsGroupReqDto;
import online.aquan.shortlink.project.dto.req.LinkStatsReqDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                        IF(count(distinct(date(create_time)))=1 and  max(create_time)>= #{startDate} and max(create_time)<= concat(#{endDate},' 23:59:59'), 1, 0) as 'new_user'
                        from t_link_access_logs
                        where gid = #{gid} and full_short_url = #{fullShortUrl}
                        group by user) as user_counts
                    """
    )
    HashMap<String, Object> getUvAndCnt(LinkStatsReqDto requestParam);


    /**
     * 查询用户在这一段时间内是不是首次访问
     * 传入了userList,这里面的用户都是在这个时间段内访问过的,只要我们判断用户第一次访问链接是不是在这个时间段就可以了
     */
    @Select(
            """
                        <script>
                        select user, 
                               case when min(create_time) between #{startDate} and #{endDate} then '新访客' else '老访客' end as 'uvType'     
                        from t_link_access_logs
                        where user in 
                        <foreach item = 'item' index = 'index' collection='userList' open = '(' separator = ',' close=')'>
                            #{item}
                        </foreach>
                        group by user
                        </script>
                    """
    )
    List<Map<String, Object>> getUvType(
            @Param("gid") String gid,
            @Param("fullShortUrl") String fullShortUrl,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("userList") List<String> userList);

    @Select(
            """
                        select 
                        count(distinct (user)) as uv,count(*) as pv,count(distinct (ip)) as uip 
                         from t_link_access_logs
                         where gid = #{gid} and full_short_url = #{fullShortUrl} and create_time between #{startDate} and concat(#{endDate},' 23:59:59')
                         group by full_short_url,gid;
                    """
    )
    LinkAccessStatsDo getPvUvUip(LinkStatsReqDto requestParam);


    @Select(
            """
                        select 
                        count(distinct (user)) as uv,count(*) as pv,count(distinct (ip)) as uip 
                         from t_link_access_logs
                         where gid = #{gid}  and create_time between #{startDate} and concat(#{endDate},' 23:59:59')
                         group by gid;
                    """
    )
    LinkAccessStatsDo getGroupPvUvUip(LinkStatsGroupReqDto requestParam);

    @Select(
            """
                                    select ip,count(*) as count from t_link_access_logs
                                    where gid = #{gid}
                                                and create_time 
                                                between concat(#{startDate},' 00:00:00') and concat(#{endDate},' 23:59:59')
                                    group by gid,ip
                                    order by count desc
                                    limit 0,5
                    """
    )
    List<HashMap<String, Object>> getGroupTopIpAndCnt(LinkStatsGroupReqDto requestParam);
    
}
