package com.ke.seckill.response;

import lombok.Data;

@Data
public class Message {

    private int code;

    private String msg;

    /**
     * 服务器错误
     */
    public static Message SERVER_ERROR = new Message(500, "SERVER_ERROR");

    public Message() {
    }

    private Message(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
