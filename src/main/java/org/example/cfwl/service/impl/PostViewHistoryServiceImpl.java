package org.example.cfwl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.cfwl.mapper.PostViewHistoryMapper;
import org.example.cfwl.model.forum.po.PostViewHistory;
import org.example.cfwl.service.PostViewHistoryService;
import org.springframework.stereotype.Service;

/**
 * @Author: 张文化
 * @Description: 帖子浏览记录Service实现类
 * @DateTime: 2025/11/27 17:25
 * @Params: 
 * @Return 
 */
@Service
public class PostViewHistoryServiceImpl extends ServiceImpl<PostViewHistoryMapper, PostViewHistory> implements PostViewHistoryService {
}