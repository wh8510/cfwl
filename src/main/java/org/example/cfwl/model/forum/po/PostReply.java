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

    /** 回复ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 帖子ID */
    @TableField("post_id")
    private Long postId;

    /** 回复用户ID */
    @TableField("user_id")
    private Long userId;

    /** 父回复ID（0表示直接回复帖子） */
    @TableField("parent_id")
    private Long parentId;

    /** 回复目标用户ID */
    @TableField("reply_to_user_id")
    private Long replyToUserId;

    /** 回复内容 */
    @TableField("content")
    private String content;

    /** 纯文本内容 */
    @TableField("content_text")
    private String contentText;

    /** 点赞数 */
    @TableField("like_count")
    private Integer likeCount;

    /** 状态：0-删除，1-正常，2-审核中 */
    @TableField("status")
    private Integer status;

    /** 楼层号 */
    @TableField("floor_number")
    private Integer floorNumber;

    /** 回复时间 */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新时间 */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    // 关联信息（非数据库字段）

    /** 用户名 */
    @TableField(exist = false)
    private String username;

    /** 回复目标用户名 */
    @TableField(exist = false)
    private String replyToUsername;

    /** 帖子标题 */
    @TableField(exist = false)
    private String postTitle;

    /** 子回复列表 */
    @TableField(exist = false)
    private List<PostReply> children;
}