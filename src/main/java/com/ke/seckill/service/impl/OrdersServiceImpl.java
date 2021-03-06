package com.ke.seckill.service.impl;

import com.ke.seckill.dto.SeckillGoodDTO;
import com.ke.seckill.entity.Orders;
import com.ke.seckill.entity.SeckillUser;
import com.ke.seckill.mapper.OrdersMapper;
import com.ke.seckill.service.IOrdersService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ke.seckill.service.ISeckillGoodsService;
import com.ke.seckill.service.ISeckillService;
import com.ke.seckill.vo.OrderDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author keke
 * @since 2019-03-10
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private ISeckillGoodsService seckillGoodsService;

    /**
     * 生成订单
     * @param user
     * @param good
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Orders createOrder(SeckillUser user, SeckillGoodDTO good) {
        Orders orders = new Orders();
        orders.setUserId(user.getId());
        orders.setGoodId(good.getGoodId());
        orders.setGoodCount(1);
        orders.setGoodName(good.getGoodName());
        orders.setGoodPrice(good.getSeckillPrice());
        orders.setDeliveryAddrId(888L);
        orders.setOrderChannel(1);
        orders.setStatus(1);
        orders.setCreateDate(new Date());
        orders.setPayDate(new Date());

        ordersMapper.createOrder(orders);

        return orders;
    }

    @Override
    public OrderDetailVO getDetail(Long orderId) {
        return ordersMapper.getDetail(orderId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Orders createOrder(long userId, long goodId) {
        SeckillGoodDTO seckillGoodDTO = seckillGoodsService.getDetailById(goodId);

        Orders orders = new Orders();
        orders.setUserId(userId);
        orders.setGoodId(goodId);
        orders.setGoodCount(1);
        orders.setGoodName(seckillGoodDTO.getGoodName());
        orders.setGoodPrice(seckillGoodDTO.getSeckillPrice());
        orders.setDeliveryAddrId(888L);
        orders.setOrderChannel(1);
        orders.setStatus(1);
        orders.setCreateDate(new Date());
        orders.setPayDate(new Date());

        ordersMapper.createOrder(orders);

        return orders;
    }
}
