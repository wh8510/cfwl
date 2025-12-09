package org.example.cfwl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.cfwl.mapper.RolePermissionMapper;
import org.example.cfwl.model.admin.po.RolePermission;
import org.example.cfwl.service.RolePermissionService;
import org.springframework.stereotype.Service;

/**
 * 角色权限关联服务实现类
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {
}