package online.aquan.shortlink.admin.dao.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.aquan.shortlink.admin.common.database.BaseDo;

@Data
@TableName("t_group")
//可以链式调用属性赋值
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupDo extends BaseDo {

    /**
     * id
     */
    private Long id;

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 分组名称
     */
    private String name;

    /**
     * 创建分组用户名
     */
    private String username;

    /**
     * 分组排序
     */
    private Integer sortOrder;
}
