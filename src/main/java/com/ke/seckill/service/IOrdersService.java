package com.ke.seckill.service;

import com.ke.seckill.dto.SeckillGoodDTO;
import com.ke.seckill.entity.Orders;
import com.baomidou.mybatisplus.service.IService;
import com.ke.seckill.entity.SeckillUser;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author keke
 * @since 2019-03-10
 */
public interface IOrdersService extends IService<Orders> {

    /**
     * 添加订单
     * @param user
     * @param good
     */
    Orders createOrder(SeckillUser user, SeckillGoodDTO good);
}
