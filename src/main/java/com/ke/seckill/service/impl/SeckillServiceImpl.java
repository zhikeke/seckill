package com.ke.seckill.service.impl;

import com.ke.seckill.dto.SeckillGoodDTO;
import com.ke.seckill.entity.Orders;
import com.ke.seckill.entity.SeckillOrders;
import com.ke.seckill.entity.SeckillUser;
import com.ke.seckill.service.IOrdersService;
import com.ke.seckill.service.ISeckillGoodsService;
import com.ke.seckill.service.ISeckillOrdersService;
import com.ke.seckill.service.ISeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 秒杀接口实现类
 */
@Service
public class SeckillServiceImpl implements ISeckillService {

    @Autowired
    private ISeckillGoodsService seckillGoodsService;
    @Autowired
    private IOrdersService ordersService;
    @Autowired
    private ISeckillOrdersService seckillOrdersService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Orders seckill(SeckillUser user, SeckillGoodDTO good) {
        // 减少库存
        int count = seckillGoodsService.reduceStock(good);

        if (count > 0) {
            // 添加订单记录
            Orders order = ordersService.createOrder(user, good);

            // 添加秒杀记录
            SeckillOrders seckillOrder = seckillOrdersService.createSeckillOrder(user.getId(), order.getId(), good.getGoodId());

            return order;
        }

        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void seckill(long userId, long goodId) {
        // 减少库存
        int count = seckillGoodsService.reduceStock(goodId);

        if (count > 0) {
            // 添加订单记录
            Orders order = ordersService.createOrder(userId, goodId);

            // 添加秒杀记录
           seckillOrdersService.createSeckillOrder(userId, order.getId(), goodId);
        }
    }
}
