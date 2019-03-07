package com.ke.seckill.controller;

import com.ke.seckill.constant.ConstantKey;
import com.ke.seckill.entity.SeckillUser;
import com.ke.seckill.redis.RedisService;
import com.ke.seckill.redis.SeckillUserKey;
import com.ke.seckill.service.ISeckillUserService;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private ISeckillUserService userService;

    /**
     * 返回商品列表页面
     * @return
     */
    @RequestMapping("/to_list")
    public String goodslist(Model model, SeckillUser seckillUser) {
        if (null != seckillUser) {
            model.addAttribute("user", seckillUser);
            return "goods_list";
        }

        return "redirect:/login/to_login";
    }
}
