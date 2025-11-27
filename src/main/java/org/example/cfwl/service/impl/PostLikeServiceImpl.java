package org.example.cfwl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.cfwl.mapper.PostLikeMapper;
import org.example.cfwl.model.forum.po.PostLike;
import org.example.cfwl.service.PostLikeService;
import org.springframework.stereotype.Service;

/**
 * @Author: 张文化
 * @Description: 帖子点赞Service实现类
 * @DateTime: 2025/11/27 17:25
 * @Params: 
 * @Return 
 */
@Service
public class PostLikeServiceImpl extends ServiceImpl<PostLikeMapper, PostLike> implements PostLikeService {
}