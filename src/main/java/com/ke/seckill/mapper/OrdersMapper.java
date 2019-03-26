package com.ke.seckill.mapper;

import com.ke.seckill.entity.Orders;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ke.seckill.vo.OrderDetailVO;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 查询订单详情
     * @param orderId 订单ID
     * @return
     */
    OrderDetailVO getDetail(@Param("orderId") Long orderId);
}
