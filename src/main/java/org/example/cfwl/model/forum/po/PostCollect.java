package org.example.cfwl.model.forum.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 帖子收藏表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("post_collect")
public class PostCollect {

    /** 收藏ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 用户ID */
    @TableField("user_id")
    private Long userId;

    /** 帖子ID */
    @TableField("post_id")
    private Long postId;

    /** 收藏时间 */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 关联信息（非数据库字段）

    /** 用户名 */
    @TableField(exist = false)
    private String username;

    /** 帖子标题 */
    @TableField(exist = false)
    private String postTitle;

    /** 帖子内容 */
    @TableField(exist = false)
    private String postContent;
}