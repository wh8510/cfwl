package org.example.cfwl.model.EmailLog.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;

/**
 * 邮件验证码记录表实体类
 */
@Data
@TableName("email_log")
public class EmailLog {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 接收邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 验证码
     */
    @TableField("verification_code")
    private String verificationCode;

    /**
     * 验证码类型：register/login/reset_password/change_email等
     */
    @TableField("code_type")
    private String codeType;

    /**
     * 发送时间
     */
    @TableField("send_time")
    private Date sendTime;

    /**
     * 过期时间
     */
    @TableField("expire_time")
    private Date expireTime;

    /**
     * 发送状态
     */
    @TableField("send_status")
    private String sendStatus;

    /**
     * 失败原因
     */
    @TableField("failure_reason")
    private String failureReason;

    /**
     * 验证时间
     */
    @TableField("verify_time")
    private Date verifyTime;

    /**
     * 验证失败次数
     */
    @TableField("failed_attempts")
    private Integer failedAttempts;

    /**
     * 最后验证尝试时间
     */
    @TableField("last_attempt_time")
    private Date lastAttemptTime;

    /**
     * 发送渠道
     */
    @TableField("send_channel")
    private String sendChannel;

    /**
     * 邮件模板ID
     */
    @TableField("template_id")
    private String templateId;

    /**
     * 今日发送次数
     */
    @TableField("today_send_count")
    private Integer todaySendCount;

    /**
     * 是否可疑
     */
    @TableField("is_suspicious")
    private Boolean isSuspicious;

    /**
     * 备注
     */
    @TableField("remarks")
    private String remarks;
}