package com.ke.seckill.vo;

import com.ke.seckill.validator.Mobile;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 登录请求数据
 */
@Data
public class LoginVo implements Serializable{
    /**
     * 手机号
     */
    @Mobile
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

}
