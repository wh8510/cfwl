package org.example.cfwl.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.cfwl.common.ErrorCode;
import org.example.cfwl.config.PasswordUtil;
import org.example.cfwl.exception.AssertionException;
import org.example.cfwl.mapper.*;
import org.example.cfwl.model.EmailLog.po.EmailLog;
import org.example.cfwl.model.Register.dto.RegisterDto;
import org.example.cfwl.model.admin.po.UserRole;
import org.example.cfwl.model.forum.vo.ForumPostSummaryInfoVo;
import org.example.cfwl.model.login.dto.LoginDto;
import org.example.cfwl.model.user.po.User;
import org.example.cfwl.service.EmailLogService;
import org.example.cfwl.service.LoginService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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
    private static final int SALT_LENGTH = 16;
    @Resource
    private UserMapper userMapper;
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private RedisTemplate<Object,Object> redisTemplate;
    @Resource
    private EmailLogMapper emailLogMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private PasswordUtil passwordUtil;
    @Override
    public User userLogin(LoginDto loginDto) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, loginDto.getUsername())
                .eq(User::getStatus,1));
        if (user == null) throw new AssertionException(ErrorCode.USER_NOT_FOUND);
        if (!passwordUtil.verify(loginDto.getPassword(), user.getPassword(), user.getSalt())) {
            throw new AssertionException(ErrorCode.WRONG_PASSWORD);
        }
        return user;
    }

    @Override
    public List<User> getUsersById(List<ForumPostSummaryInfoVo> forumPostSummaryInfoVos) {
        return userMapper.selectBatchIds(forumPostSummaryInfoVos.stream().map(ForumPostSummaryInfoVo::getUserId).collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public User registerUserInfo(RegisterDto registerDto) {
        Judgment(registerDto);
        // 查询邮箱验证码
        EmailLog emailLog = emailLogMapper.selectOne(new LambdaQueryWrapper<EmailLog>().eq(EmailLog::getEmail, registerDto.getEmail())
                .orderByDesc(EmailLog::getSendTime));
        // 校验验证码
        if (StringUtils.isBlank(registerDto.getCode())) {
            throw new AssertionException(ErrorCode.OPERATION_ERROR);
        }
        if (redisTemplate.opsForValue().get(registerDto.getEmail())==null){
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
        redisTemplate.delete(registerDto.getEmail());
        emailLog.setVerifyTime(new Date());
        emailLog.setLastAttemptTime(new Date());
        emailLogMapper.updateById(emailLog);
        User user = new User();
        user.setUsername(registerDto.getUsername());
        String salt = RandomUtil.randomString(SALT_LENGTH);
        user.setPassword(passwordUtil.encryptWithSalt(registerDto.getPassword(),salt));
        user.setSalt(salt);
        user.setEmail(registerDto.getEmail());
        user.setPhone(registerDto.getPhone());
        user.setStatus(1);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(2L);
        userRole.setCreateTime(LocalDateTime.now());
        userRoleMapper.insert(userRole);
        return user;
    }
    //判断是否重复
    private void Judgment(RegisterDto registerDto){
        // 分别查询，可以精确判断是哪个字段重复
        Long usernameCount = userMapper.selectCount(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, registerDto.getUsername()));
        Long phoneCount = userMapper.selectCount(new LambdaQueryWrapper<User>()
                .eq(User::getPhone, registerDto.getPhone()));
        Long emailCount = userMapper.selectCount(new LambdaQueryWrapper<User>()
                .eq(User::getEmail, registerDto.getEmail()));

        if (usernameCount > 0) {
            throw new AssertionException(ErrorCode.USERNAME);
        }
        if (emailCount > 0) {
            throw new AssertionException(ErrorCode.EMAIL);
        }
        if (phoneCount > 0) {
            throw new AssertionException(ErrorCode.PHONE);
        }
    }
}
