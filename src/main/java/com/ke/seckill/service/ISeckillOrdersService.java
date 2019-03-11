package com.ke.seckill.service;

import com.ke.seckill.entity.SeckillOrders;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author keke
 * @since 2019-03-10
 */
public interface ISeckillOrdersService extends IService<SeckillOrders> {

    /**
     * 根据用户id和商品id判断用户是否有秒杀订单存在
     *
     * @param userId 用户id
     * @param goodId 商品id
     * @return
     */
    SeckillOrders selectOrderByUserIdAndGoodId(Long userId, long goodId);

    /**
     * 添加秒杀记录
     *
     * @param userId  用户id
     * @param orderId 订单id
     * @param goodId  商品id
     * @return
     */
    SeckillOrders createSeckillOrder(Long userId, Long orderId, Long goodId);
}
