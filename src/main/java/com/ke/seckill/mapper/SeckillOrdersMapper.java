package com.ke.seckill.mapper;

import com.ke.seckill.entity.SeckillOrders;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author keke
 * @since 2019-03-10
 */
public interface SeckillOrdersMapper extends BaseMapper<SeckillOrders> {

    /**
     * 根据用户id和商品id判断用户是否有秒杀订单存在
     * @param userId 用户id
     * @param goodId 商品id
     * @return
     */
    SeckillOrders selectOrderByUserIdAndGoodId(@Param("userId") Long userId, @Param("goodId") long goodId);

    /**
     * 创建秒杀订单
     * @param seckillOrder
     */
    long createSeckillOrder(SeckillOrders seckillOrder);
}
