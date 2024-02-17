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

    private final StringRedisTemplate stringRedisTemplate;
    
   /* 这里定义的是不需要token就可以使用的接口的url,但是由于/api/short-link/admin/v1/user这个接口的restful形式
    导致了需要单独处理这个接口,处理方法就是判断接口的发送请求的method*/

    private static final List<String> IGNORE_URI = Lists.newArrayList(
            "/api/short-link/admin/v1/user/login",
            "/api/short-link/admin/v1/user/has-username"
    );

    @Override
    /*
     * sneaky --暗中的
     * 使用 @SneakyThrows 注解后，Lombok 会自动为方法中的受检查异常添加一个 try-catch 块，
     * 并将异常转换为 RuntimeException 的子类。这样，你就不需要在代码中显式处理异常，从而简化了代码*/
    @SneakyThrows
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //转为HttpServlet
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        //获取url
        String requestURI = httpServletRequest.getRequestURI();
        //如果不是登录或者判断用户名是否使用的url
        if (!IGNORE_URI.contains(requestURI)) {
            String method = httpServletRequest.getMethod();
            //如果不是新增用户的接口
            if (!(Objects.equals(requestURI, "/api/short-link/admin/v1/user") && Objects.equals("POST", method))) {
                String username = httpServletRequest.getHeader("username");
                String token = httpServletRequest.getHeader("token");
                //如果username或者token缺失,返回失败响应
                //同时这里使用原生的response的形式返回,因为全局异常处理器捕获不到过滤器抛出的异常
                if (!StrUtil.isAllNotBlank(username, token)) {
                    returnJson((HttpServletResponse) servletResponse,
                            JSON.toJSONString(Results.failure(new ClientException(UserErrorCodeEnums.USER_TOKEN_FAIL))));
                    return;
                }
                Object userInfoJsonStr = null;
                try {
                    userInfoJsonStr = stringRedisTemplate.opsForHash().get(USER_LOGIN_KEY + username, token);
                    if (userInfoJsonStr == null) {
                        throw new ClientException(UserErrorCodeEnums.USER_TOKEN_FAIL);
                    }
                } catch (Exception e) {
                    returnJson((HttpServletResponse) servletResponse,
                            JSON.toJSONString(Results.failure(new ClientException(UserErrorCodeEnums.USER_TOKEN_FAIL))));
                }
                //将取出的json数据转化成对象
                assert userInfoJsonStr != null;
                UserInfoDto userInfoDTO = JSON.parseObject(userInfoJsonStr.toString(), UserInfoDto.class);
                //存入线程中
                UserContext.setUser(userInfoDTO);
            }
        }
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            //最后移除当前用户
            UserContext.removeUser();
        }
    }

    private void returnJson(HttpServletResponse response, String json) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.print(json);
        }
    }
}