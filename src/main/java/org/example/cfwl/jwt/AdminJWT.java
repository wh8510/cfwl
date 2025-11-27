package org.example.cfwl.jwt;

import lombok.extern.slf4j.Slf4j;
import org.example.cfwl.context.BaseContext;
import org.example.cfwl.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@Component
public class AdminJWT implements HandlerInterceptor {
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Value("${token.refreshTime}")
    private Long refreshTime;
    @Value("${token.expiresTime}")
    private Long expiresTime;
    /**
     * 权限认证的拦截操作.
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        log.info("=======进入拦截器========");
        // 如果不是映射到方法直接通过,可以访问资源.
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        //为空就返回错误
        String token = httpServletRequest.getHeader("AdminToken");
        if (null == token || "".equals(token.trim())) {
            return false;
        }
        log.info("==============token:" + token);
        Map<String, String> map = tokenUtil.adminToken(token);
        String adminId = map.get("adminId");
        String adminName = map.get("adminName");
        // String adminRole = map.get("adminRole");
        long timeOfUse = System.currentTimeMillis() - Long.parseLong(map.get("timeStamp"));
        BaseContext.setCurrentId(Long.valueOf(adminId));
        BaseContext.setCurrentName(adminName);
        //1.判断 token 是否过期
        if (timeOfUse < refreshTime&&redisTemplate.opsForValue().get(token)!=null) {
            log.info("token验证成功");
            return true;
        }
        //token过期就返回 token 无效.
        else {
            log.error("token 无效");
            throw new AccountNotFoundException("token无效");
        }
    }
}