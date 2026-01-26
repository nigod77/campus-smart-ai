package com.nijiahao.common.core.exception;

import com.nijiahao.common.core.domain.ResultCode;
import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {
    private Integer code;

    public ServiceException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(String msg){
        super(msg);
        this.code = 500;
    }

    public ServiceException(ResultCode resultCode) {
        // 1. 把枚举里的 msg 传给父类 RuntimeException
        super(resultCode.getMsg());
        // 2. 把枚举里的 code 赋值给当前类的 code
        this.code = resultCode.getCode();
    }

    public ServiceException(ResultCode resultCode , String msg) {
        // 1. 把枚举里的 msg 传给父类 RuntimeException
        super(resultCode.getMsg()+msg);
        // 2. 把枚举里的 code 赋值给当前类的 code
        this.code = resultCode.getCode();
    }
}
