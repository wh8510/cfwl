package org.example.cfwl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.cfwl.model.EmailLog.po.EmailLog;
/**
 * @Author: 张文化
 * @Description: 邮件日志Mapper接口
 * @DateTime: 2025/12/08 16:34
 * @Params:
 * @Return
 */
@Mapper
public interface EmailLogMapper extends BaseMapper<EmailLog> {
}
