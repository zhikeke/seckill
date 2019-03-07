package com.ke.seckill.exception;

import com.ke.seckill.response.ResponseMessage;
import lombok.Data;

/**
 * 全局异常格式
 */
@Data
public class GlobalException extends RuntimeException {

    private ResponseMessage responseMessage;

    public GlobalException(ResponseMessage message) {
        this.responseMessage = message;
    }

}
