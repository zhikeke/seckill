package com.ke.seckill.controller;

import com.ke.seckill.entity.SeckillUser;
import com.ke.seckill.response.Response;
import com.ke.seckill.service.IOrdersService;
import com.ke.seckill.vo.OrderDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private IOrdersService ordersService;

    /**
     * 查询订单详情
     * @param orderId 订单ID
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public Response detail(@RequestParam("orderId") Long orderId) {
        OrderDetailVO orderDetail = ordersService.getDetail(orderId);

        return Response.success(orderDetail);
    }


}
