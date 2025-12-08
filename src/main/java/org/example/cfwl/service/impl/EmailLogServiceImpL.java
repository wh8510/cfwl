package org.example.cfwl.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.cfwl.mapper.EmailLogMapper;
import org.example.cfwl.model.EmailLog.po.EmailLog;
import org.example.cfwl.service.EmailLogService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 张文化
 * @Description: 邮件日志ServiceImpl接口
 * @DateTime: 2025/12/08 16:34
 * @Params:
 * @Return
 */
@Service
public class EmailLogServiceImpL extends ServiceImpl<EmailLogMapper, EmailLog> implements EmailLogService {
    @Resource
    private RedisTemplate<Object,Object> redisTemplate;
    @Value("${email.loseTime}")
    private Long loseTime;
    @Override
    public boolean sendEmail(String email) {
        // 设置邮件服务器属性
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.qq.com"); // 设置邮件服务器主机名
        properties.put("mail.smtp.port", "587"); // 设置邮件服务器端口号
        properties.put("mail.smtp.auth", "true"); // 启用身份验证
        properties.put("mail.smtp.starttls.enable", "true"); // 启用 TLS
        //properties.put("mail.smtp.socketFactory.port", "465"); // 设置 SSL 端口
        //properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // 设置 SSL Socket Factory
        //properties.put("mail.smtp.socketFactory.fallback", "false"); // 禁用 SSL 回退
        // 创建会话对象
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("2798165022@qq.com", "dxsfpdpiipuvdhfd");
                // 在这里填写发送邮件的邮箱地址和密码/授权码
            }
        });
        String code = RandomUtil.randomString(6);
        try {
            // 创建邮件消息
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("2798165022@qq.com")); // 设置发件人邮箱
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email)); // 设置收件人邮箱
            message.setSubject("验证码"); // 设置邮件主题
            message.setText(code); // 设置邮件内容
            // 发送邮件
            Transport.send(message);
            //记录日志
            saveEmailLog(email,code,new MessagingException());
            //保存code在redis
            redisTemplate.opsForValue().set(email, code,loseTime, TimeUnit.MINUTES);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace(); // 打印异常堆栈信息
            saveEmailLog(email,code,e);
            return false;
        }
    }

    private void saveEmailLog(String email,String code,MessagingException e) {
        EmailLog emailLog = new EmailLog();
        emailLog.setEmail(email);
        emailLog.setVerificationCode(code);
        emailLog.setCodeType("1");
        emailLog.setSendTime(new Date());
        emailLog.setExpireTime(new Date(System.currentTimeMillis() + 300000));
        emailLog.setSendStatus("1");
        emailLog.setFailureReason(e.toString());
        emailLog.setSendChannel("QQ邮箱");
        baseMapper.insert(emailLog);
    }
}
