package org.example.cfwl.model.forum.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 帖子浏览记录表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("post_view_history")
public class PostViewHistory {

    /** 浏览记录ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 用户ID（可为空，支持匿名浏览） */
    @TableField("user_id")
    private Long userId;

    /** 帖子ID */
    @TableField("post_id")
    private Long postId;

    /** IP地址 */
    @TableField("ip_address")
    private String ipAddress;

    /** 用户代理 */
    @TableField("user_agent")
    private String userAgent;

    /** 浏览时长（秒） */
    @TableField("view_duration")
    private Integer viewDuration;

    /** 浏览时间 */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 关联信息（非数据库字段）

    /** 用户名 */
    @TableField(exist = false)
    private String username;

    /** 帖子标题 */
    @TableField(exist = false)
    private String postTitle;

    /** 帖子摘要 */
    @TableField(exist = false)
    private String postSummary;
}