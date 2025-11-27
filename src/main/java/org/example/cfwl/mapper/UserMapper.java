package org.example.cfwl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.cfwl.model.user.po.User;

/**
 * @Author: 张文化
 * @Description: $
 * @DateTime: 2025/11/25$ 10:39$
 * @Params: $
 * @Return $
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
