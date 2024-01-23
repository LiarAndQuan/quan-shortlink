package online.aquan.shortlink.admin.dto.resp;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResDto {

    /**
     * 返回token
     */
    private String token;
}
