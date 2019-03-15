package com.ke.seckill.mapper;

import com.ke.seckill.entity.Orders;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author keke
 * @since 2019-03-10
 */
public interface OrdersMapper extends BaseMapper<Orders> {

    /**
     * 创建订单
     * @param orders
     */
    int createOrder(Orders orders);
}
