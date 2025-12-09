package org.example.cfwl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.cfwl.mapper.PermissionMapper;
import org.example.cfwl.model.admin.po.Permission;
import org.example.cfwl.service.PermissionService;
import org.springframework.stereotype.Service;

/**
 * 权限服务实现类
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
}