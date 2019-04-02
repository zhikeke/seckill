package com.ke.seckill.listener;

import com.ke.seckill.config.RabbitConfig;
import com.ke.seckill.dto.SeckillGoodDTO;
import com.ke.seckill.entity.OrderMessage;
import com.ke.seckill.entity.Orders;
import com.ke.seckill.redis.RedisService;
import com.ke.seckill.service.ISeckillGoodsService;
import com.ke.seckill.service.ISeckillService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class RabbitMQListener {

    @Autowired
    private RedisService redisService;
    @Autowired
    private ISeckillGoodsService seckillGoodsService;
    @Autowired
    private ISeckillService seckillService;


    @RabbitListener(queues = RabbitConfig.SECKILL_QUEUE)
    public void orderSaveMessage(@Payload String message, Channel channel, @Headers Map<String, Object> header) throws Exception{
        try {
            OrderMessage orderMessage = RedisService.stringToBean(message, OrderMessage.class);

            // 查询redis 中是否存在用户订单

            // 查询数据库是否有库存
            SeckillGoodDTO seckillGoodDTO = seckillGoodsService.getDetailById(orderMessage.getGoodId());
            if (null == seckillGoodDTO || seckillGoodDTO.getStock() <= 0) {
                return;
            }

            seckillService.seckill(orderMessage.getUserId(), orderMessage.getGoodId());
        } catch (Exception e) {
            log.error("Catch OrderSaveMessage Exception: {}", e);
        } finally {
            Long deliveryTag = (Long) header.get(AmqpHeaders.DELIVERY_TAG);

            // 手动ack
            channel.basicAck(deliveryTag, false);
        }
    }

}
