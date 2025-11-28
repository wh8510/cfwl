package org.example.cfwl.model.forum.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 帖子点赞表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("post_like")
public class PostLike {

    /** 点赞ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 用户ID */
    @TableField("user_id")
    private Long userId;

    /** 帖子ID */
    @TableField("post_id")
    private Long postId;

    /** 点赞时间 */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 关联信息（非数据库字段）

    /** 用户名 */
    @TableField(exist = false)
    private String username;

    /** 帖子标题 */
    @TableField(exist = false)
    private String postTitle;
}