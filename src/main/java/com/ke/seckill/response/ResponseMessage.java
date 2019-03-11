package com.ke.seckill.response;

import lombok.Data;

@Data
public class ResponseMessage {

    private int code;

    private String msg;


    //通用的错误码
    public static ResponseMessage SUCCESS = new ResponseMessage(0, "success");
    public static ResponseMessage PARAM_NULL = new ResponseMessage(500000, "请求参数为空");
    public static ResponseMessage SERVER_ERROR = new ResponseMessage(500100, "服务端异常");
    public static ResponseMessage BIND_ERROR = new ResponseMessage(500101, "参数校验异常：%s");
    public static ResponseMessage REQUEST_ILLEGAL = new ResponseMessage(500102, "请求非法");
    public static ResponseMessage ACCESS_LIMIT_REACHED= new ResponseMessage(500104, "访问太频繁！");


    //登录模块 5002XX
    public static ResponseMessage SESSION_ERROR = new ResponseMessage(500210, "Session不存在或者已经失效");
    public static ResponseMessage PASSWORD_EMPTY = new ResponseMessage(500211, "登录密码不能为空");
    public static ResponseMessage NICKNAME_EMPTY = new ResponseMessage(500212, "用户名不能为空");
    public static ResponseMessage NICKNAME_NOT_EXIST = new ResponseMessage(500214, "用户名不存在");
    public static ResponseMessage PASSWORD_ERROR = new ResponseMessage(500215, "密码错误");


    //商品模块 5003XX


    //订单模块 5004XX
    public static ResponseMessage ORDER_NOT_EXIST = new ResponseMessage(500400, "订单不存在");

    //秒杀模块 5005XX
    public static ResponseMessage SECKILL_OVER = new ResponseMessage(500500, "商品已经秒杀完毕");
    public static ResponseMessage REPEATE_SECKILL = new ResponseMessage(500501, "不能重复秒杀");
    public static ResponseMessage SECKILL_FAIL = new ResponseMessage(500502, "秒杀失败");
    public static ResponseMessage SECKILL_NOT_START = new ResponseMessage(500503, "秒杀活动还没开始");



    public ResponseMessage() {
    }

    private ResponseMessage(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseMessage fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new ResponseMessage(code, message);
    }
}
