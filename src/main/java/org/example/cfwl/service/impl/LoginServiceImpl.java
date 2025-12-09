package org.example.cfwl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.cfwl.common.ErrorCode;
import org.example.cfwl.exception.AssertionException;
import org.example.cfwl.mapper.AdminMapper;
import org.example.cfwl.mapper.EmailLogMapper;
import org.example.cfwl.mapper.LoginMapper;
import org.example.cfwl.mapper.UserMapper;
import org.example.cfwl.model.EmailLog.po.EmailLog;
import org.example.cfwl.model.Register.dto.RegisterDto;
import org.example.cfwl.model.forum.vo.ForumPostSummaryInfoVo;
import org.example.cfwl.model.login.dto.LoginDto;
import org.example.cfwl.model.user.po.User;
import org.example.cfwl.service.EmailLogService;
import org.example.cfwl.service.LoginService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: 张文化
 * @Description: $
 * @DateTime: 2025/11/25$ 09:42$
 * @Params: $
 * @Return $
 */
@Service
public class LoginServiceImpl extends ServiceImpl<UserMapper, User> implements LoginService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private RedisTemplate<Object,Object> redisTemplate;
    @Resource
    private EmailLogMapper emailLogMapper;
    @Override
    public User userLogin(LoginDto loginDto) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, loginDto.getUsername())
                .eq(User::getPassword, loginDto.getPassword())
                .eq(User::getStatus,1));
        if (user == null) throw new AssertionException(ErrorCode.PARAMS_ERROR);
        return user;
    }

    @Override
    public List<User> getUsersById(List<ForumPostSummaryInfoVo> forumPostSummaryInfoVos) {
        return userMapper.selectBatchIds(forumPostSummaryInfoVos.stream().map(ForumPostSummaryInfoVo::getUserId).collect(Collectors.toList()));
    }

    @Override
    public User registerUserInfo(RegisterDto registerDto) {
        EmailLog emailLog = emailLogMapper.selectOne(new LambdaQueryWrapper<EmailLog>().eq(EmailLog::getEmail, registerDto.getEmail())
                .orderByDesc(EmailLog::getSendTime));
        // 校验验证码
        if (StringUtils.isBlank(registerDto.getCode())) {
            throw new AssertionException(ErrorCode.OPERATION_ERROR);
        }
        String code = Objects.requireNonNull(redisTemplate.opsForValue().get(registerDto.getEmail())).toString();
        if (!Objects.equals(registerDto.getCode(), code)){
            emailLog.setVerifyTime(new Date());
            emailLog.setLastAttemptTime(new Date());
            emailLog.setFailedAttempts(emailLog.getFailedAttempts() + 1);
            emailLogMapper.updateById(emailLog);
            throw new AssertionException(ErrorCode.OPERATION_ERROR);
        }
        emailLog.setVerifyTime(new Date());
        emailLog.setLastAttemptTime(new Date());
        emailLogMapper.updateById(emailLog);
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(registerDto.getPassword());
        user.setEmail(registerDto.getEmail());
        user.setPhone(registerDto.getPhone());
        userMapper.insert(user);

        return user;
    }
}
