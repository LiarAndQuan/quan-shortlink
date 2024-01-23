package online.aquan.shortlink.admin.controller;


import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import online.aquan.shortlink.admin.common.convention.result.Result;
import online.aquan.shortlink.admin.common.convention.result.Results;
import online.aquan.shortlink.admin.dto.req.UserLoginReqDto;
import online.aquan.shortlink.admin.dto.req.UserRegisterReqDto;
import online.aquan.shortlink.admin.dto.req.UserUpdateReqDto;
import online.aquan.shortlink.admin.dto.resp.UserActualRespDto;
import online.aquan.shortlink.admin.dto.resp.UserLoginResDto;
import online.aquan.shortlink.admin.dto.resp.UserRespDto;
import online.aquan.shortlink.admin.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 根据用户名查询用户脱敏信息
     */
    @GetMapping("/api/short-link/admin/v1/user/{username}")
    public Result<UserRespDto> getUserByUsername(@PathVariable String username) {
        return Results.success(userService.getUserByUsername(username));
    }

    /**
     * 根据用户名查询用户的真实信息
     */
    @GetMapping("/api/short-link/admin/v1/actual/user/{username}")
    public Result<UserActualRespDto> getActualUserByUsername(@PathVariable String username) {
        return Results.success(BeanUtil.toBean(userService.getUserByUsername(username), UserActualRespDto.class));
    }

    /**
     * 查询用户名是否存在
     */
    @GetMapping("/api/short-link/admin/v1/user/has-username")
    public Result<Boolean> hasUsername(@RequestParam String username) {
        return Results.success(userService.hasUsername(username));
    }

    /**
     * 用户注册
     */
    @PostMapping("/api/short-link/admin/v1/user")
    public Result<Void> register(@RequestBody UserRegisterReqDto requestParam) {
        userService.register(requestParam);
        return Results.success();
    }

    /**
     * 修改用户信息
     */
    @PutMapping("/api/short-link/admin/v1/user")
    public Result<Void> update(@RequestBody UserUpdateReqDto requestParam) {
        //todo 检查用户是否是登录状态
        userService.update(requestParam);
        return Results.success();
    }

    /**
     * 用户登录
     */
    @PostMapping("/api/short-link/admin/v1/user/login")
    public Result<UserLoginResDto> login(@RequestBody UserLoginReqDto requestParam) {
        return Results.success(userService.login(requestParam));
    }

    @GetMapping("/api/short-link/admin/v1/user/check-login")
    public Result<Boolean> hasLogin(@RequestParam String username, @RequestParam String token) {
        return Results.success(userService.hasLogin(username,token));
    }
    
    @DeleteMapping("/api/short-link/admin/v1/user/logout")
    public Result<Void> logout(@RequestParam String username,@RequestParam String token){
        userService.logout(username,token);
        return Results.success();
    }


}
