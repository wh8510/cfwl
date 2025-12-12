package org.example.cfwl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.cfwl.model.admin.po.Permission;

import java.util.List;

/**
 * 权限Mapper接口
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 根据用户ID查询用户的所有权限标识符
     */
    List<String> selectUserPermissions(@Param("userId") Long userId);

    /**
     * 根据用户ID查询用户角色
     */
    List<String> selectUserRoles(@Param("userId") Long userId);

}