package online.aquan.shortlink.admin.common.biz.user;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;
import java.util.Objects;

/**
 * 通过继承Filter并且实现doFilter方法,可以添加一个过滤器
 * 每当用户请求到来时可以取出用户名和token,然后从redis中查询出用户的信息,然后存入threadLocal中
 */
@RequiredArgsConstructor
public class UserTransmitFilter implements Filter {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //转为HttpServlet
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        //获取url
        String requestURI = httpServletRequest.getRequestURI();
        //如果不是登录的url
        if (!Objects.equals(requestURI, "/api/short-link/v1/user/login")) {
            //获取名字和token之后从redis取出用户的json数据
            String username = httpServletRequest.getHeader("username");
            String token = httpServletRequest.getHeader("token");
            Object userInfoJsonStr = stringRedisTemplate.opsForHash().get("login_" + username, token);
            if (userInfoJsonStr != null) {
                //将取出的json数据转化成对象
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
}