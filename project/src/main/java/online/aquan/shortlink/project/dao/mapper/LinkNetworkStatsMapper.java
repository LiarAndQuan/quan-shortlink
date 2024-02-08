package online.aquan.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.aquan.shortlink.project.dao.entity.LinkNetworkStatsDo;
import online.aquan.shortlink.project.dto.req.LinkStatsGroupReqDto;
import online.aquan.shortlink.project.dto.req.LinkStatsReqDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LinkNetworkStatsMapper extends BaseMapper<LinkNetworkStatsDo> {
    @Insert("""
            insert into t_link_network_stats(full_short_url, gid, date, cnt, network, create_time, update_time, del_flag)
             VALUES (#{fullShortUrl},#{gid},#{date},#{cnt},#{network},#{createTime},#{updateTime},0) 
             On duplicate key update cnt = cnt +1,update_time = #{date};
            """)
    void insertOrUpdate(LinkNetworkStatsDo linkNetworkStatsDo);


    @Select(
            """
                                    select network,sum(cnt) as cnt from t_link_network_stats
                                    where gid = #{gid} and full_short_url=#{fullShortUrl} 
                                                and date between #{startDate} and #{endDate}
                                    group by gid,full_short_url,network
                    """
    )
    List<LinkNetworkStatsDo> getNetworkAndCnt(LinkStatsReqDto requestParam);

    @Select(
            """
                                    select network,sum(cnt) as cnt from t_link_network_stats
                                    where gid = #{gid} 
                                                and date between #{startDate} and #{endDate}
                                    group by gid,network
                    """
    )
    List<LinkNetworkStatsDo> getGroupNetworkAndCnt(LinkStatsGroupReqDto requestParam);
}
