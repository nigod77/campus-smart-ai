package com.nijiahao.llm.config;

import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.util.SaResult;
import com.nijiahao.common.core.domain.Result;
import com.nijiahao.common.core.domain.ResultCode;
import com.nijiahao.common.core.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public Result<?> handleServiceException(ServiceException e) {
        return Result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler
    public SaResult handlerException(Exception e) {
        e.printStackTrace();
        return SaResult.error(e.getMessage());
    }
    // 2. 拦截：权限不足异常 (角色/权限)
    @ExceptionHandler({NotRoleException.class, NotPermissionException.class})
    public Result<Void> handlerNotPermissionException(SaTokenException e) {
        log.warn("鉴权报错: 权限不足 -> {}", e.getMessage());
        return Result.error(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMsg());
    }
}
