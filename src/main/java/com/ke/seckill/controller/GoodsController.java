package com.ke.seckill.controller;

import com.ke.seckill.constant.GoodKey;
import com.ke.seckill.dto.SeckillGoodDTO;
import com.ke.seckill.entity.SeckillUser;
import com.ke.seckill.redis.RedisService;
import com.ke.seckill.service.ISeckillGoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.context.webflux.SpringWebFluxContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private ISeckillGoodsService seckillGoodsService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;


    /**
     * 返回商品列表页面
     * @return
     */
    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String goodslist(HttpServletRequest request, HttpServletResponse response, Model model, SeckillUser seckillUser) {
        // 先从缓存中查找数据
        String html = redisService.get(GoodKey.GET_GOODS_LIST, "", String.class);

        // 如果存在页面缓存，直接返回
        if (StringUtils.isNoneEmpty(html)) {
            return html;
        }

        if (null != seckillUser) {
            model.addAttribute("user", seckillUser);
        }
        List<SeckillGoodDTO> seckillGoods = seckillGoodsService.getAll();
        model.addAttribute("seckillGoods", seckillGoods);

        WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());

        // 将页面缓存保存到redis中
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);

        if (StringUtils.isNotEmpty(html)) {
            redisService.set(GoodKey.GET_GOODS_LIST, "", html);
        }

        return html;
    }

    /**
     * 返回商品详情
     * @param model
     * @param seckillUser
     * @param goodId 商品id
     * @return
     */
    @RequestMapping(value = "/to_detail/{goodId}", produces = "text/html")
    @ResponseBody
    public String goodDetail(HttpServletRequest request, HttpServletResponse response,
                             Model model, SeckillUser seckillUser, @PathVariable("goodId") long goodId) {
        String html = redisService.get(GoodKey.GET_GOOD_DETAIL, String.valueOf(goodId), String.class);

        if (StringUtils.isNotEmpty(html)) {
            return html;
        }


        if (null != seckillUser) {
            model.addAttribute("user", seckillUser);
        }

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

        WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());

        // 将页面缓存保存到redis中
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);

        if (StringUtils.isNotEmpty(html)) {
            redisService.set(GoodKey.GET_GOOD_DETAIL, String.valueOf(goodId), html);
        }

        return html;
    }
}
