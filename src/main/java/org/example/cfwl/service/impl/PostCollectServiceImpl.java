package org.example.cfwl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.cfwl.mapper.PostCollectMapper;
import org.example.cfwl.model.forum.po.PostCollect;
import org.example.cfwl.service.PostCollectService;
import org.springframework.stereotype.Service;

/**
 * @Author: 张文化
 * @Description: 帖子收藏Service实现类
 * @DateTime: 2025/11/27 17:25
 * @Params: 
 * @Return 
 */
@Service
public class PostCollectServiceImpl extends ServiceImpl<PostCollectMapper, PostCollect> implements PostCollectService {
}