package online.aquan.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.aquan.shortlink.project.dao.entity.LinkNetworkStatsDo;
import org.apache.ibatis.annotations.Insert;

public interface LinkNetworkStatsMapper extends BaseMapper<LinkNetworkStatsDo> {
    @Insert("""
            insert into t_link_network_stats(full_short_url, gid, date, cnt, network, create_time, update_time, del_flag)
             VALUES (#{fullShortUrl},#{gid},#{date},#{cnt},#{network},#{createTime},#{updateTime},0) 
             On duplicate key update cnt = cnt +1,update_time = #{date};
            """)
    void insertOrUpdate(LinkNetworkStatsDo linkNetworkStatsDo);
}
