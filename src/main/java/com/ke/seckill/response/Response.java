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
     * 请求成功
     * @return
     */
    public Response success() {
        this.code = 0;
        this.msg = "success";
        return this;
    }

    /**
     * 带返回数据的请求成功
     * @param data 返回数据
     * @return
     */
    public Response success(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
        return this;
    }

    public Response error(Message message) {
        this.code = message.getCode();
        this.msg = message.getMsg();
        return this;
    }

}
