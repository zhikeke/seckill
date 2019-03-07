package com.ke.seckill.response;

import lombok.Data;

@Data
public class Response<T> {

    private int code;

    private String msg;

    private T data;

    public Response() {
    }

    /**
     *  成功时候的调用
     * */
    public static  <T> Response<T> success(T data){
        return new Response<T>(data);
    }

    /**
     *  失败时候的调用
     * */
    public static  <T> Response<T> error(ResponseMessage message){
        return new Response<>(message);
    }

    private Response(T data) {
        this.data = data;
    }

    private Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Response(ResponseMessage message) {
        if(message != null) {
            this.code = message.getCode();
            this.msg = message.getMsg();
        }
    }
}
