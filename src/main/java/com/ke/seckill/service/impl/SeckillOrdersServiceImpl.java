package com.ke.seckill.service.impl;

import com.ke.seckill.entity.SeckillOrders;
import com.ke.seckill.mapper.SeckillOrdersMapper;
import com.ke.seckill.service.ISeckillOrdersService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author keke
 * @since 2019-03-10
 */
@Service
public class SeckillOrdersServiceImpl extends ServiceImpl<SeckillOrdersMapper, SeckillOrders> implements ISeckillOrdersService {

    @Autowired
    private SeckillOrdersMapper seckillOrdersMapper;

    @Override
    public SeckillOrders selectOrderByUserIdAndGoodId(Long userId, long goodId) {
        return seckillOrdersMapper.selectOrderByUserIdAndGoodId(userId, goodId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SeckillOrders createSeckillOrder(Long userId, Long orderId, Long goodId) {
        SeckillOrders seckillOrder = new SeckillOrders();
        seckillOrder.setUserId(userId);
        seckillOrder.setGoodsId(goodId);
        seckillOrder.setOrderId(orderId);

        long id = seckillOrdersMapper.createSeckillOrder(seckillOrder);

        seckillOrder.setId(id);

        return seckillOrder;
    }
}
