package org.example.cfwl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.cfwl.model.forum.vo.ForumPostSummaryInfoVo;
import org.example.cfwl.model.login.dto.LoginDto;
import org.example.cfwl.model.user.po.User;

import java.util.List;

/**
 * @Author: 张文化
 * @Description: $
 * @DateTime: 2025/11/25$ 09:40$
 * @Params: $
 * @Return $
 */
public interface LoginService extends IService<User> {
    User userLogin(LoginDto loginDto);
    //获取用户信息
    List<User> getUsersById(List<ForumPostSummaryInfoVo> forumPostSummaryInfoVos);
}
