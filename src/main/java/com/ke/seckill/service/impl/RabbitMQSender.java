package com.ke.seckill.service.impl;

import com.ke.seckill.config.RabbitConfig;
import com.ke.seckill.entity.OrderMessage;
import com.ke.seckill.redis.RedisService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {
    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 发送保存订单消息
     * @param orderMessage {@link OrderMessage}
     */
    public void sendSaveOrderMessage(OrderMessage orderMessage) {
        String msg = RedisService.beanToString(orderMessage);

        amqpTemplate.convertAndSend(RabbitConfig.SECKILL_EXCHANGE, "seckill.save.order.new", msg);
    }
}
