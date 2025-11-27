package org.example.cfwl.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.cfwl.common.BaseResponse;
import org.example.cfwl.config.PreAuthorize;
import org.example.cfwl.context.BaseContext;
import org.example.cfwl.model.login.dto.LoginDto;
import org.example.cfwl.model.user.po.User;
import org.example.cfwl.model.user.vo.UserVo;
import org.example.cfwl.service.LoginService;
import org.example.cfwl.util.ResultUtil;
import org.example.cfwl.util.TokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    /**
     * @Author: 张文化
     * @Description: $登录接口
     * @DateTime: 2025/11/25$ 09:23$
     * @Params: $loginDto
     * @Return $UserVo
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
     * @Author: 张文化
     * @Description: $获取用户信息接口
     * @DateTime: 2025/11/25$ 09:23$
     */
    @PreAuthorize("login:userInfo")
    @GetMapping("/userInfo")
    private BaseResponse<User> userInfo() {
        User user = loginService.getById(BaseContext.getCurrentId());
        System.out.println(BaseContext.getCurrentId());
        return ResultUtil.success(user);
    }
}
