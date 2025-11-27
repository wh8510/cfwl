package org.example.cfwl.config;

import java.lang.annotation.*;

/**
 * 增强版权限验证注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PreAuthorize {
    
    /**
     * 需要的权限标识符
     * 支持多个权限，用逗号分隔
     * 例如：system:user:query,system:user:export
     */
    String value();
    
    /**
     * 验证逻辑：AND 或 OR
     */
    Logical logical() default Logical.AND;
    
    /**
     * 是否启用权限验证
     * 默认true，设置为false时跳过权限验证
     */
    boolean enabled() default true;
}