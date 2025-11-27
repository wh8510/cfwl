package org.example.cfwl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.cfwl.model.forum.po.ForumPost;

/**
 * @Author: 张文化
 * @Description: 论坛帖子Mapper接口
 * @DateTime: 2025/11/27 17:25
 * @Params: 
 * @Return 
 */
@Mapper
public interface ForumPostMapper extends BaseMapper<ForumPost> {
}