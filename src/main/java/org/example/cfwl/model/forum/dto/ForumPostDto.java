package org.example.cfwl.model.forum.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 论坛帖子dto表
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ForumPostDto {

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
}
