package com.ke.seckill.controller;

import com.ke.seckill.response.Response;
import com.ke.seckill.service.ISeckillUserService;
import com.ke.seckill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private ISeckillUserService userService;

    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    /**
     * 登录校验
     * @return
     */
    @ResponseBody
    @RequestMapping("/do_login")
    public Response doLogin(@Validated LoginVo loginVo, HttpServletResponse response) {
        userService.login(response, loginVo);
        return Response.success(true);
    }
}
