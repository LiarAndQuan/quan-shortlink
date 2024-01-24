package online.aquan.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import online.aquan.shortlink.admin.dao.entity.UserDo;
import online.aquan.shortlink.admin.dto.req.UserLoginReqDto;
import online.aquan.shortlink.admin.dto.req.UserRegisterReqDto;
import online.aquan.shortlink.admin.dto.req.UserUpdateReqDto;
import online.aquan.shortlink.admin.dto.resp.UserLoginResDto;
import online.aquan.shortlink.admin.dto.resp.UserRespDto;

public interface UserService extends IService<UserDo> {

    UserRespDto getUserByUsername(String username);

    Boolean hasUsername(String username);

    void register(UserRegisterReqDto requestParam);

    void update(UserUpdateReqDto requestParam);

    UserLoginResDto login(UserLoginReqDto requestParam);

    Boolean hasLogin(String username, String token);

    void logout(String username, String token);
}
