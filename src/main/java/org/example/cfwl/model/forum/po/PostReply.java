package org.example.cfwl.model.forum.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 帖子回复表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("post_reply")
public class PostReply {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    @TableField("post_id")
    private Long postId;
    
    @TableField("user_id")
    private Long userId;
    
    @TableField("parent_id")
    private Long parentId;
    
    @TableField("reply_to_user_id")
    private Long replyToUserId;
    
    @TableField("content")
    private String content;
    
    @TableField("content_text")
    private String contentText;
    
    @TableField("like_count")
    private Integer likeCount;
    
    @TableField("status")
    private Integer status;
    
    @TableField("floor_number")
    private Integer floorNumber;
    
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    // 关联信息（非数据库字段）
    @TableField(exist = false)
    private String username;
    
    @TableField(exist = false)
    private String replyToUsername;
    
    @TableField(exist = false)
    private String postTitle;
    
    @TableField(exist = false)
    private List<PostReply> children; // 子回复列表
}