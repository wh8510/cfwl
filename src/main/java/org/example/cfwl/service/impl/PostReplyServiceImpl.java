package org.example.cfwl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.cfwl.mapper.PostReplyMapper;
import org.example.cfwl.model.forum.po.PostReply;
import org.example.cfwl.service.PostReplyService;
import org.springframework.stereotype.Service;

/**
 * @Author: 张文化
 * @Description: 帖子回复Service实现类
 * @DateTime: 2025/11/27 17:25
 * @Params: 
 * @Return 
 */
@Service
public class PostReplyServiceImpl extends ServiceImpl<PostReplyMapper, PostReply> implements PostReplyService {
}