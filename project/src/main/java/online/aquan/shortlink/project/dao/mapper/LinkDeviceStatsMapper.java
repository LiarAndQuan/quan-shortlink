package online.aquan.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.aquan.shortlink.project.dao.entity.LinkDeviceStatsDo;
import org.apache.ibatis.annotations.Insert;

public interface LinkDeviceStatsMapper extends BaseMapper<LinkDeviceStatsDo> {
    @Insert("""
            insert into t_link_device_stats(full_short_url, gid, date, cnt, device, create_time, update_time, del_flag)
             VALUES (#{fullShortUrl},#{gid},#{date},#{cnt},#{device},#{createTime},#{updateTime},0) 
             On duplicate key update cnt = cnt +1,update_time = #{date};
            """)
    void insertOrUpdate(LinkDeviceStatsDo linkDeviceStatsDo);
}
