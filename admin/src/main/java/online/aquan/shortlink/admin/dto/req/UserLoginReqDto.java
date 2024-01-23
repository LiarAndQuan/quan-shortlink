package online.aquan.shortlink.admin.dto.req;


import lombok.Data;

@Data
public class UserLoginReqDto {
    
    private String username;
    
    private String password;
}
