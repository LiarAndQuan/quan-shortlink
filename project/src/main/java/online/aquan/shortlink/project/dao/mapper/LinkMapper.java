package online.aquan.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.aquan.shortlink.project.dao.entity.LinkDo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface LinkMapper extends BaseMapper<LinkDo> {

    @Update(
            """
                       update t_link set total_pv = total_pv+ #{pv} , total_uv = total_uv+#{uv} , total_uip = total_uip +#{uip}
                        where gid = #{gid} and full_short_url = #{fullShortUrl} 
                    """
    )
    void incrementStat(@Param("gid") String gid,
                       @Param("fullShortUrl") String fullShortUrl,
                       @Param("pv") Integer pv,
                       @Param("uv") Integer uv,
                       @Param("uip") Integer uip);
}
