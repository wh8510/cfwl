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
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    @TableField("user_id")
    private Long userId;
    
    @TableField("post_id")
    private Long postId;
    
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    // 关联信息（非数据库字段）
    @TableField(exist = false)
    private String username;
    
    @TableField(exist = false)
    private String postTitle;
    
    @TableField(exist = false)
    private String postContent;
}