package org.example.cfwl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.cfwl.mapper.ForumPostMapper;
import org.example.cfwl.model.forum.dto.ForumPostSearchDto;
import org.example.cfwl.model.forum.po.ForumPost;
import org.example.cfwl.service.ForumPostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 张文化
 * @Description: 论坛帖子Service实现类
 * @DateTime: 2025/11/27 17:25
 * @Params: 
 * @Return 
 */
@Service
public class ForumPostServiceImpl extends ServiceImpl<ForumPostMapper, ForumPost> implements ForumPostService {
    @Resource
    private ForumPostMapper forumPostMapper;
    @Override
    public List<ForumPost> getForumSummaryInfo() {
        return forumPostMapper.selectList(new LambdaQueryWrapper<ForumPost>().eq(ForumPost::getStatus,1));
    }

    @Override
    public List<ForumPost> getForumSummaryInfoByKey(ForumPostSearchDto forumPostSearchDto) {
        return forumPostMapper.selectList(new LambdaQueryWrapper<ForumPost>().eq(ForumPost::getStatus,1)
                .like(ForumPost::getTitle, forumPostSearchDto.getKeyword()).or()
                .like(ForumPost::getContentText, forumPostSearchDto.getKeyword()).or()
                .like(ForumPost::getSummary,forumPostSearchDto.getKeyword()));
    }
}