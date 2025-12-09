package org.example.cfwl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.cfwl.mapper.UserRoleMapper;
import org.example.cfwl.model.admin.po.UserRole;
import org.example.cfwl.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户角色关联服务实现类
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}