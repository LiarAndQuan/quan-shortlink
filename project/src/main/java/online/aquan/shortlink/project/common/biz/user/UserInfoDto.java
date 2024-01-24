package online.aquan.shortlink.project.common.biz.user;


import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * 用户信息实体
 */
@Data
public class UserInfoDto {

    /**
     * 用户 ID
     */
    @JSONField(name = "id")
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realName;
}
