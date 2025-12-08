package org.example.cfwl.controller;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.cfwl.common.BaseResponse;
import org.example.cfwl.config.PreAuthorize;
import org.example.cfwl.context.BaseContext;
import org.example.cfwl.model.Register.dto.RegisterDto;
import org.example.cfwl.model.login.dto.LoginDto;
import org.example.cfwl.model.user.po.User;
import org.example.cfwl.model.user.vo.UserVo;
import org.example.cfwl.service.EmailLogService;
import org.example.cfwl.service.LoginService;
import org.example.cfwl.util.Result;
import org.example.cfwl.util.ResultUtil;
import org.example.cfwl.util.TokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 张文化
 * @Description: $登录接口
 * @DateTime: 2025/11/25$ 09:23$
 * @Params: $
 * @Return $
 */
@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {
    @Value("${token.refreshTime}")
    private Long refreshTime;
    @Resource
    private LoginService loginService;
    @Resource
    private TokenUtil tokenUtil;
    @Resource
    private RedisTemplate<Object,Object> redisTemplate;
    @Resource
    private EmailLogService emailLogService;
    /**
     * 登录
     *
     * @param loginDto 用户信息
     * @return UserVo
     */
    @PostMapping("/user")
    private BaseResponse<UserVo> login(@RequestBody LoginDto loginDto) {
        log.info("个人信息：{}", loginDto);
        User user = loginService.userLogin(loginDto);
        String token = tokenUtil.getUserToken(user.getId(),user.getUsername());
        UserVo userVo = BeanUtil.copyProperties(user, UserVo.class);
        userVo.setToken(token);
        redisTemplate.opsForValue().set(token, userVo,refreshTime, TimeUnit.MILLISECONDS);
        Long time = redisTemplate.getExpire(token);
        log.info(String.valueOf(time));
        return ResultUtil.success(userVo);
    }

    /**
     * 获取登录用户信息
     *
     * @return User
     */
    @PreAuthorize("login:userInfo")
    @GetMapping("/userInfo")
    private BaseResponse<User> userInfo() {
        User user = loginService.getById(BaseContext.getCurrentId());
        System.out.println(BaseContext.getCurrentId());
        return ResultUtil.success(user);
    }
    @PreAuthorize("login:logout")
    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) {
        try {
            // 获取token
            String token = request.getHeader("UserToken");
            // 1. 清理 Redis 中的 token
            redisTemplate.delete(token);
            // 2. 清理用户权限缓存
            String cacheKey = "user_permissions:" + BaseContext.getCurrentId();
            redisTemplate.delete(cacheKey);
            // 3. 清理 ThreadLocal 上下文
            BaseContext.clearCurrentName();
            log.info("用户退出登录成功");
            return Result.success("退出登录成功");
        } catch (Exception e) {
            log.error("退出登录异常", e);
            return Result.error("退出登录失败");
        }
    }

    /**
     * 注册
     *
     * @param registerDto 用户信息
     * @return UserVo
     */
    @PostMapping("/register")
    private BaseResponse<UserVo> register(@RequestBody RegisterDto registerDto) {
        log.info("个人信息：{}", registerDto);

        return null;
    }
}
