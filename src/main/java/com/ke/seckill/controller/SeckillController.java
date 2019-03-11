package com.ke.seckill.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ke.seckill.dto.SeckillGoodDTO;
import com.ke.seckill.entity.Orders;
import com.ke.seckill.entity.SeckillOrders;
import com.ke.seckill.entity.SeckillUser;
import com.ke.seckill.response.ResponseMessage;
import com.ke.seckill.service.IGoodsService;
import com.ke.seckill.service.ISeckillGoodsService;
import com.ke.seckill.service.ISeckillOrdersService;
import com.ke.seckill.service.ISeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/seckill")
public class SeckillController {
    @Autowired
    private ISeckillGoodsService seckillGoodsService;
    @Autowired
    private ISeckillOrdersService seckillOrdersService;
    @Autowired
    private ISeckillService seckillService;

    /**
     * 秒杀
     * @param model
     * @param user
     * @param goodId 商品id
     * @return
     */
    @RequestMapping("/do_seckill")
    public String doSeckill(Model model, SeckillUser user, @RequestParam("goodId") long goodId) {
         if (null == user) {
             return "redirect:/login/to_login";
         }

        SeckillGoodDTO good = seckillGoodsService.getDetailById(goodId);

         if (null != good) {
             long currentTime = System.currentTimeMillis();

             // 库存不足或时间超时， 提示秒杀结束
             if (good.getStock() <= 0 || currentTime > good.getEndDate().getTime()) {
                 model.addAttribute("errmsg", ResponseMessage.SECKILL_OVER.getMsg());
                 return "seckill_fail";
             }

             if (currentTime < good.getStartDate().getTime()) {
                 model.addAttribute("errmsg", ResponseMessage.SECKILL_NOT_START.getMsg());
                 return "seckill_fail";
             }

             // 判断是否已经存在秒杀的订单
             SeckillOrders seckillOrder = seckillOrdersService.selectOrderByUserIdAndGoodId(user.getId(), goodId);

             if (null != seckillOrder) {
                 model.addAttribute("errmsg", ResponseMessage.REPEATE_SECKILL.getMsg());
                 return "seckill_fail";
             }

             // 减库存， 下订单， 写入秒杀订单
             Orders order = seckillService.seckill(user, good);
             model.addAttribute("orderInfo", order);
             model.addAttribute("good", good);
             return "order_detail";
         }

         return "redirect:/goods/to_list";
    }


}
