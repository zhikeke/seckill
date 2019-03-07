package com.ke.seckill.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 登录请求数据
 */
@Data
public class LoginVo implements Serializable{
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String nickname;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

}
