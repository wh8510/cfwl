package org.example.cfwl.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PermissionMapper {
    
    /**
     * 根据用户ID查询用户的所有权限标识符
     */
    List<String> selectUserPermissions(@Param("userId") Long userId);
    
    /**
     * 根据用户ID查询用户角色
     */
    List<String> selectUserRoles(@Param("userId") Long userId);
}