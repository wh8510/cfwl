package org.example.cfwl.model.user.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 张文化
 * @Description: $
 * @DateTime: 2025/11/25$ 09:34$
 * @Params: $
 * @Return $
 */
@Data
public class UserVo implements Serializable {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String token;
}
