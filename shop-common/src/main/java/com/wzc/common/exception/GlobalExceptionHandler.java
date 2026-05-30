package com.wzc.common.exception;

import com.wzc.common.constant.CommonConstant;
import com.wzc.common.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResultUtil<Void> handleBusinessException(BusinessException e) {
        log.error("业务异常: {}", e.getMessage());
        return ResultUtil.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultUtil<Void> handleValidationException(MethodArgumentNotValidException e) {
        log.error("参数校验异常: {}", e.getMessage());
        String message = e.getBindingResult().getFieldError() != null
                ? e.getBindingResult().getFieldError().getDefaultMessage()
                : "参数校验失败";
        return ResultUtil.fail(400, message);
    }

    @ExceptionHandler(BindException.class)
    public ResultUtil<Void> handleBindException(BindException e) {
        log.error("参数绑定异常: {}", e.getMessage());
        String message = e.getFieldError() != null
                ? e.getFieldError().getDefaultMessage()
                : "参数绑定失败";
        return ResultUtil.fail(400, message);
    }

    @ExceptionHandler(Exception.class)
    public ResultUtil<Void> handleException(Exception e) {
        log.error("系统异常: ", e);
        return ResultUtil.fail(CommonConstant.FAIL_CODE, "系统异常，请联系管理员");
    }
}
