package online.aquan.shortlink.admin.dto.resp;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import online.aquan.shortlink.admin.common.serialize.PhoneDesensitizationSerializer;

@Data
public class UserRespDto {

    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    @JsonSerialize(using = PhoneDesensitizationSerializer.class)
    private String phone;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 注销时间戳
     */
    private Long deletionTime;
}
