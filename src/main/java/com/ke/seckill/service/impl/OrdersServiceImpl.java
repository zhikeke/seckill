package com.ke.seckill.service.impl;

import com.ke.seckill.entity.Orders;
import com.ke.seckill.mapper.OrdersMapper;
import com.ke.seckill.service.IOrdersService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
