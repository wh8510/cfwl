package org.example.cfwl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.cfwl.model.forum.po.PostLike;

/**
 * @Author: 张文化
 * @Description: 帖子点赞Mapper接口
 * @DateTime: 2025/11/27 17:25
 * @Params: 
 * @Return 
 */
@Mapper
public interface PostLikeMapper extends BaseMapper<PostLike> {
}