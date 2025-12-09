package org.example.cfwl.model.admin.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 权限表
 */
@Data
@TableName("permission")
public class Permission {

    /**
     * 权限ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 父权限ID（用于构建树形结构）
     */
    private Long parentId;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限标识符（例如：user:add, system:user:query）
     */
    private String perms;

    /**
     * 类型：0-目录，1-菜单，2-按钮
     */
    private Integer type;

    /**
     * 路由地址（前端）
     */
    private String path;

    /**
     * 组件路径（前端）
     */
    private String component;

    /**
     * 图标
     */
    private String icon;

    /**
     * 显示排序
     */
    private Integer sortOrder;

    /**
     * 状态：0-禁用，1-正常
     */
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}