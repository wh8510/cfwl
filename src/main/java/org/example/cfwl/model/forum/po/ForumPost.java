package org.example.cfwl.model.forum.po;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

/**
 * 论坛帖子表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("forum_post")
@Document(indexName = "forum")
public class ForumPost {

    /** 帖子ID */
    @TableId(value = "id", type = IdType.AUTO)
    @Id
    private Long id;

    /** 发布用户ID */
    @TableField("user_id")
    private Long userId;

    /** 帖子标题 */
    @TableField("title")
    private String title;

    /** 帖子内容 */
    @TableField("content")
    private String content;

    /** 纯文本内容（用于搜索） */
    @TableField("content_text")
    private String contentText;

    /** 帖子摘要 */
    @TableField("summary")
    private String summary;

    /** 分类ID */
    @TableField("category_id")
    private Long categoryId;

    /** 标签（逗号分隔） */
    @TableField("tags")
    private String tags;

    /** 浏览数 */
    @TableField("view_count")
    private Integer viewCount;

    /** 点赞数 */
    @TableField("like_count")
    private Integer likeCount;

    /** 收藏数 */
    @TableField("collect_count")
    private Integer collectCount;

    /** 评论数 */
    @TableField("comment_count")
    private Integer commentCount;

    /** 状态：0-删除，1-正常，2-审核中，3-审核失败 */
    @TableField("status")
    private Integer status;

    /** 是否置顶：0-否，1-是 */
    @TableField("is_top")
    private Boolean isTop;

    /** 是否精华：0-否，1-是 */
    @TableField("is_essence")
    private Boolean isEssence;

    /** 是否允许评论：0-否，1-是 */
    @TableField("is_comment")
    private Boolean isComment;

    /** 最后回复时间 */
    @TableField("last_reply_time")
    private LocalDateTime lastReplyTime;

    /** 发布时间 */
    @TableField(value = "publish_time", fill = FieldFill.INSERT)
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",  // 与 ES 中的日期格式完全一致（空格分隔）
            timezone = "GMT+8"               // 时区（根据你的实际时区调整，如 Asia/Shanghai）
    )
    private LocalDateTime publishTime;

    /** 更新时间 */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",  // 与 ES 中的日期格式完全一致（空格分隔）
            timezone = "GMT+8"               // 时区（根据你的实际时区调整，如 Asia/Shanghai）
    )
    private LocalDateTime updateTime;
}