package org.example.cfwl.model.forum.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 论坛帖子表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("forum_post")
public class ForumPost {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    @TableField("user_id")
    private Long userId;
    
    @TableField("title")
    private String title;
    
    @TableField("content")
    private String content;
    
    @TableField("content_text")
    private String contentText;
    
    @TableField("summary")
    private String summary;
    
    @TableField("category_id")
    private Long categoryId;
    
    @TableField("tags")
    private String tags;
    
    @TableField("view_count")
    private Integer viewCount;
    
    @TableField("like_count")
    private Integer likeCount;
    
    @TableField("collect_count")
    private Integer collectCount;
    
    @TableField("comment_count")
    private Integer commentCount;
    
    @TableField("status")
    private Integer status;
    
    @TableField("is_top")
    private Boolean isTop;
    
    @TableField("is_essence")
    private Boolean isEssence;
    
    @TableField("is_comment")
    private Boolean isComment;
    
    @TableField("last_reply_time")
    private LocalDateTime lastReplyTime;
    
    @TableField(value = "publish_time", fill = FieldFill.INSERT)
    private LocalDateTime publishTime;
    
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    // 关联用户信息（非数据库字段）
    @TableField(exist = false)
    private String username;
    
    @TableField(exist = false)
    private String nickname;
}