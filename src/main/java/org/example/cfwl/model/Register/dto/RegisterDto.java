package org.example.cfwl.model.Register.dto;

import lombok.Data;

@Data
public class RegisterDto {
    /**
     * 用户名（登录账号）
     */
    private String username;

    /**
     * 密码（加密后）
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;
}
