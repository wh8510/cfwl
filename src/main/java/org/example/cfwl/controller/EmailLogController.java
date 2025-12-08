package org.example.cfwl.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.cfwl.common.BaseResponse;
import org.example.cfwl.common.ErrorCode;
import org.example.cfwl.model.EmailLog.po.EmailLog;
import org.example.cfwl.service.EmailLogService;
import org.example.cfwl.util.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
/**
 * @Author: 张文化
 * @Description: $发送邮箱接口
 * @DateTime: 2025/12/08$ 17:21$
 * @Params: $
 * @Return $
 */
@RestController
@RequestMapping("/emilLog")
@Slf4j
public class EmailLogController {

    @Resource
    private EmailLogService emailLogService;
    /**
     * 发送邮件
     *
     * @param email 邮箱
     */
    @GetMapping("/sendEmail")
    public BaseResponse<Object> sendEmail(@RequestParam String email) {
        boolean b = emailLogService.sendEmail(email);
        if (b) return ResultUtil.success("邮件验证码发送成功");
        else return ResultUtil.error(ErrorCode.PARAMS_ERROR);
    }
}
