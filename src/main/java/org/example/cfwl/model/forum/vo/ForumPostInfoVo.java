package org.example.cfwl.model.forum.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class ForumPostInfoVo {

    /** 帖子ID */
    @TableId(value = "id", type = IdType.AUTO)
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

    /** 发布时间 */
    @TableField(value = "publish_time", fill = FieldFill.INSERT)
    private LocalDateTime publishTime;

    /** 更新时间 */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    // 关联用户信息（非数据库字段）
    private String username;
}
