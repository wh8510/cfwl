package org.example.cfwl.jwt;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.cfwl.config.PreAuthorize;
import org.example.cfwl.context.BaseContext;
import org.example.cfwl.exception.AssertionException;
import org.example.cfwl.mapper.PermissionMapper;
import org.example.cfwl.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 张文化
 * @Description: $
 * @DateTime: 2025/11/24$ 13:56$
 * @Params: $
 * @Return $
 */
@Slf4j
@Component
public class UserJWT implements HandlerInterceptor {
    @Autowired
    TokenUtil tokenUtil;
    @Value("${token.refreshTime}")
    private Long refreshTime;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Value("${token.expiresTime}")
    private Long expiresTime;
    @Resource
    private PermissionMapper permissionMapper;
    /**
     * 权限认证的拦截操作.
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        log.info("=======进入拦截器========");

        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();

        // 检查token
        String token = httpServletRequest.getHeader("UserToken");
        if (null == token || "".equals(token.trim())) {
            return false;
        }

        log.info("==============token:" + token);
        Map<String, String> map = tokenUtil.userToken(token);
        String userId = map.get("userId");
        String userName = map.get("userName");
        long timeOfUse = System.currentTimeMillis() - Long.parseLong(map.get("timeStamp"));

        BaseContext.setCurrentId(Long.valueOf(userId));
        BaseContext.setCurrentName(userName);

        // token过期验证
        if (timeOfUse >= refreshTime || redisTemplate.opsForValue().get(token) == null) {
            log.error("token 无效");
            throw new AssertionException(403,"token无效");
        }

        log.info("token验证成功");

        // 权限验证 - 检查方法上的@PreAuthorize注解
        PreAuthorize preAuthorize = method.getAnnotation(PreAuthorize.class);
        if (preAuthorize != null) {
            String requiredPermission = preAuthorize.value();
            log.info("需要权限: {}", requiredPermission);

            // 验证用户是否有该权限
            if (!hasPermission(Long.valueOf(userId), requiredPermission)) {
                log.error("用户 {} 没有权限访问: {}", userName, requiredPermission);
                throw new AssertionException(403,"没有访问权限");
            }
        }

        return true;
    }

    /**
     * 检查用户是否有指定权限
     */
    private boolean hasPermission(Long userId, String requiredPermission) {
        // 从数据库查询用户的所有权限
        List<String> userPermissions = getUserPermissions(userId);

        // 检查是否包含所需权限
        return userPermissions.contains(requiredPermission);
    }

    /**
     * 查询用户的所有权限标识符（使用MyBatis Mapper）
     */
    private List<String> getUserPermissions(Long userId) {
        // 使用缓存提高性能
        String cacheKey = "user_permissions:" + userId;
        List<String> permissions = (List<String>) redisTemplate.opsForValue().get(cacheKey);

        if (permissions == null) {
            // 使用MyBatis Mapper查询数据库
            permissions = permissionMapper.selectUserPermissions(userId);

            // 缓存权限，设置过期时间
            if (permissions != null && !permissions.isEmpty()) {
                redisTemplate.opsForValue().set(cacheKey, permissions, 30, TimeUnit.MINUTES);
            }
        }

        return permissions != null ? permissions : new ArrayList<>();
    }
}
