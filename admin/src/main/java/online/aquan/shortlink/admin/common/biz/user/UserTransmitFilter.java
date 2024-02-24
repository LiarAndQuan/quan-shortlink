package online.aquan.shortlink.admin.common.biz.user;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import online.aquan.shortlink.admin.common.convention.exception.ClientException;
import online.aquan.shortlink.admin.common.convention.result.Results;
import online.aquan.shortlink.admin.common.enums.UserErrorCodeEnums;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

import static online.aquan.shortlink.admin.common.constant.RedisCacheConstant.USER_LOGIN_KEY;

/**
 * 通过继承Filter并且实现doFilter方法,可以添加一个过滤器
 * 每当用户请求到来时可以取出用户名和token,然后从redis中查询出用户的信息,然后存入threadLocal中
 */

@RequiredArgsConstructor
public class UserTransmitFilter implements Filter {

   
   /* 这里定义的是不需要token就可以使用的接口的url,但是由于/api/short-link/admin/v1/user这个接口的restful形式
    导致了需要单独处理这个接口,处理方法就是判断接口的发送请求的method*/
    
    /*
     * sneaky --暗中的
     * 使用 @SneakyThrows 注解后，Lombok 会自动为方法中的受检查异常添加一个 try-catch 块，
     * 并将异常转换为 RuntimeException 的子类。这样，你就不需要在代码中显式处理异常，从而简化了代码*/
    @Override
    @SneakyThrows
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String username = httpServletRequest.getHeader("username");
        if (StrUtil.isNotBlank(username)) {
            String userId = httpServletRequest.getHeader("userId");
            String realName = httpServletRequest.getHeader("realName");
            UserInfoDto userInfoDTO = new UserInfoDto(userId, username, realName);
            UserContext.setUser(userInfoDTO);
        }
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            UserContext.removeUser();
        }
    }
    
}