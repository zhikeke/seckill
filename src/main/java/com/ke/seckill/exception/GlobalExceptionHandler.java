package com.ke.seckill.exception;

import com.ke.seckill.response.ResponseMessage;
import com.ke.seckill.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 全局异常处理器
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Response exceptionHandler(HttpServletRequest request, Exception e) {
        log.error("请求异常:{}", e);

        if (e instanceof GlobalException) {
            GlobalException exception = (GlobalException) e;
            return Response.error(exception.getResponseMessage());
        } else if (e instanceof BindException) {
             BindException ex = (BindException) e;

             List<ObjectError> errors = ex.getAllErrors();
             ObjectError error = errors.get(0);
             String msg = error.getDefaultMessage();

             return Response.error(ResponseMessage.BIND_ERROR.fillArgs(msg));
         }

         return Response.error(ResponseMessage.SERVER_ERROR);
    }

}
