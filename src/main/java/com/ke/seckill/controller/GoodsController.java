package com.ke.seckill.controller;

import com.ke.seckill.constant.ConstantKey;
import com.ke.seckill.dto.SeckillGoodDTO;
import com.ke.seckill.entity.SeckillUser;
import com.ke.seckill.redis.RedisService;
import com.ke.seckill.redis.SeckillUserKey;
import com.ke.seckill.service.ISeckillGoodsService;
import com.ke.seckill.service.ISeckillUserService;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private ISeckillUserService userService;
    @Autowired
    private ISeckillGoodsService seckillGoodsService;

    /**
     * 返回商品列表页面
     * @return
     */
    @RequestMapping("/to_list")
    public String goodslist(Model model, SeckillUser seckillUser) {
        if (null != seckillUser) {
            model.addAttribute("user", seckillUser);

            List<SeckillGoodDTO> seckillGoods = seckillGoodsService.getAll();
            model.addAttribute("seckillGoods", seckillGoods);

            return "goods_list";
        }

        return "redirect:/login/to_login";
    }

    /**
     * 返回商品详情
     * @param model
     * @param seckillUser
     * @param goodId 商品id
     * @return
     */
    @RequestMapping("/to_detail/{goodId}")
    public String goodDetail(Model model, SeckillUser seckillUser, @PathVariable("goodId") long goodId) {
        if (null != seckillUser) {
            model.addAttribute("user", seckillUser);

            SeckillGoodDTO good = seckillGoodsService.getDetailById(goodId);

            if (null != good) {
                long currentTimeMillions = System.currentTimeMillis();
                long seckillStartTime = good.getStartDate().getTime();
                long seckillEndTime = good.getEndDate().getTime();

                if (currentTimeMillions < seckillStartTime) {
                    model.addAttribute("remainSeconds", seckillStartTime - currentTimeMillions);
                    model.addAttribute("seckillStatus", 0);
                } else if (currentTimeMillions > seckillEndTime) {
                    model.addAttribute("seckillStatus", 2);
                } else {
                    model.addAttribute("seckillStatus", 1);
                }
            }

            model.addAttribute("good", good);

            return "goods_detail";
        }

        return "redirect:/login/to_login";
    }
}
