package online.aquan.shortlink.admin.common.biz.user;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import online.aquan.shortlink.admin.common.convention.errorcode.BaseErrorCode;
import online.aquan.shortlink.admin.common.convention.exception.ClientException;
import online.aquan.shortlink.admin.common.convention.result.Results;
import online.aquan.shortlink.admin.config.UserFlowRiskControlConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.io.PrintWriter;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UserFlowRiskControlFilter implements Filter {

    private final StringRedisTemplate stringRedisTemplate;
    private final UserFlowRiskControlConfiguration userFlowRiskControlConfiguration;
    private static final String USER_FLOW_RISK_CONTROL_LUA_SCRIPT_PATH = "lua/user_flow_risk_control.lua";

    @Override
    @SneakyThrows
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        //配置lua脚本的执行路径
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(USER_FLOW_RISK_CONTROL_LUA_SCRIPT_PATH)));
        redisScript.setResultType(Long.class);
        //获取用户,如果获取不到,说明是UserTransmitFilter里面放行的几个接口
        String username = Optional.ofNullable(UserContext.getUsername()).orElse("other");
        Long result = null;
        try {
            result = stringRedisTemplate.execute(redisScript, Lists.newArrayList(username), userFlowRiskControlConfiguration.getTimeWindow());
        } catch (Exception e) {
            log.error("执行用户请求流量限制LUA脚本出错", e);
            returnJson((HttpServletResponse) servletResponse, JSON.toJSONString(Results.failure(new ClientException(BaseErrorCode.FLOW_LIMIT_ERROR))));
        }
        if (result == null || result > userFlowRiskControlConfiguration.getMaxAccessCount()) {
            returnJson((HttpServletResponse) servletResponse, JSON.toJSONString(Results.failure(new ClientException(BaseErrorCode.FLOW_LIMIT_ERROR))));
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void returnJson(HttpServletResponse response, String json) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.print(json);
        }
    }
}
