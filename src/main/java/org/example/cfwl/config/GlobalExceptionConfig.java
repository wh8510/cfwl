package org.example.cfwl.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cfwl.common.BaseResponse;
import org.example.cfwl.common.ErrorCode;
import org.example.cfwl.exception.AssertionException;
import org.example.cfwl.util.ResultUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 自定义全局异常
 *
 * @author ZERO
 * @date 2024/2/28
 */
@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionConfig {

    @ExceptionHandler(value = AssertionException.class)
    public BaseResponse<?> assertionExceptionHandler(Exception e) {
        log.error("AssertionException", e);
        return ResultUtil.error(((AssertionException) e).getCode(), e.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(Exception e) {
        log.error("RuntimeException", e);
        return ResultUtil.error(ErrorCode.SYSTEM_ERROR);
    }

}
