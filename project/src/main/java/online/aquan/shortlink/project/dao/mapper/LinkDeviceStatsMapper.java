package online.aquan.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.aquan.shortlink.project.dao.entity.LinkDeviceStatsDo;
import online.aquan.shortlink.project.dto.req.LinkStatsGroupReqDto;
import online.aquan.shortlink.project.dto.req.LinkStatsReqDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LinkDeviceStatsMapper extends BaseMapper<LinkDeviceStatsDo> {
    @Insert("""
            insert into t_link_device_stats(full_short_url, gid, date, cnt, device, create_time, update_time, del_flag)
             VALUES (#{fullShortUrl},#{gid},#{date},#{cnt},#{device},#{createTime},#{updateTime},0) 
             On duplicate key update cnt = cnt +1,update_time = #{date};
            """)
    void insertOrUpdate(LinkDeviceStatsDo linkDeviceStatsDo);


    @Select(
            """
                                    select device,sum(cnt) as cnt from t_link_device_stats
                                    where gid = #{gid} and full_short_url=#{fullShortUrl} 
                                                and date between #{startDate} and #{endDate}
                                    group by gid,full_short_url,device
                    """
    )
    List<LinkDeviceStatsDo> getDeviceAndCnt(LinkStatsReqDto requestParam);

    @Select(
            """
                                    select device,sum(cnt) as cnt from t_link_device_stats
                                    where gid = #{gid} 
                                                and date between #{startDate} and #{endDate}
                                    group by gid,device
                    """
    )
    List<LinkDeviceStatsDo> getGroupDeviceAndCnt(LinkStatsGroupReqDto requestParam);
}
