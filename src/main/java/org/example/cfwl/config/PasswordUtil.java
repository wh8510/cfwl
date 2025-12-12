package org.example.cfwl.config;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {
    
    private static final int SALT_LENGTH = 16;
    private static final DigestAlgorithm ALGORITHM = DigestAlgorithm.MD5;
    
    /**
     * 生成盐值
     */
    public String generateSalt() {
        // Hutool 生成随机盐值
        return RandomUtil.randomString(SALT_LENGTH);
    }
    
    /**
     * MD5加密（带盐）
     */
    public String encryptWithSalt(String password, String salt) {
        // 方法1：使用 Digester
        Digester md5 = new Digester(ALGORITHM);
        // 密码 + 盐值
        String combined = password + salt;
        return md5.digestHex(combined);
        
        // 方法2：一行代码实现
        // return DigestUtil.md5Hex(password + salt);
    }
    
    /**
     * 验证密码
     */
    public boolean verify(String inputPassword, String storedPassword, String salt) {
        String encrypted = encryptWithSalt(inputPassword, salt);
        return StrUtil.equals(encrypted, storedPassword);
    }
    
    /**
     * 生成密码（密码 + 盐值）
     */
    public PasswordResult generate(String rawPassword) {
        String salt = generateSalt();
        String encrypted = encryptWithSalt(rawPassword, salt);
        return new PasswordResult(encrypted, salt);
    }
    
    /**
     * 更安全的加密方式：加盐 + 多次迭代
     */
    public String encryptWithIterations(String password, String salt, int iterations) {
        Digester md5 = new Digester(ALGORITHM);
        String combined = password + salt;
        
        // 第一次加密
        String hash = md5.digestHex(combined);
        
        // 多次迭代增加安全性
        for (int i = 1; i < iterations; i++) {
            hash = md5.digestHex(hash + salt);
        }
        
        return hash;
    }
    
    @Data
    @AllArgsConstructor
    public static class PasswordResult {
        private String encryptedPassword;
        private String salt;
    }
}