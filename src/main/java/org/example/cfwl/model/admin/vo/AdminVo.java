package org.example.cfwl.model.admin.vo;

import lombok.Data;

/**
 * @Author: 张文化
 * @Description: $
 * @DateTime: 2025/11/25$ 09:28$
 * @Params: $
 * @Return $
 */
@Data
public class AdminVo {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String token;
}
