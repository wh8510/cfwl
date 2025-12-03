package org.example.cfwl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.cfwl.common.ErrorCode;
import org.example.cfwl.exception.AssertionException;
import org.example.cfwl.mapper.AdminMapper;
import org.example.cfwl.mapper.LoginMapper;
import org.example.cfwl.mapper.UserMapper;
import org.example.cfwl.model.forum.vo.ForumPostSummaryInfoVo;
import org.example.cfwl.model.login.dto.LoginDto;
import org.example.cfwl.model.user.po.User;
import org.example.cfwl.service.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
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
}
