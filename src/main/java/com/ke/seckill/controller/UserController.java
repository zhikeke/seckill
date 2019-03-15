package com.ke.seckill.controller;

import com.ke.seckill.entity.SeckillUser;
import com.ke.seckill.response.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/info")
    @ResponseBody
    public Response<SeckillUser> getUserInfo(SeckillUser seckillUser) {
        return Response.success(seckillUser);
    }

}
