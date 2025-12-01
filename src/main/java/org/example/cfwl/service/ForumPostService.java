package org.example.cfwl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.cfwl.model.forum.po.ForumPost;

import java.util.List;

/**
 * @Author: 张文化
 * @Description: 论坛帖子Service接口
 * @DateTime: 2025/11/27 17:25
 * @Params: 
 * @Return 
 */
public interface ForumPostService extends IService<ForumPost> {
    /**
     * 获取帖子概要信息
     *
     * @return List<ForumPostSummaryInfoVo>
     */
    List<ForumPost> getForumSummaryInfo();
}