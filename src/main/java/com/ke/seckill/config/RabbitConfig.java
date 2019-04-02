package com.ke.seckill.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    /**
     * 秒杀exchange
     */
    public static final String  SECKILL_EXCHANGE = "seckill_exchange";

    /**
     * 秒杀queue
     */
    public static final String SECKILL_QUEUE = "seckill.save.order.queue";

    /**
     * 声明exchange
     * @return
     */
    @Bean
    public TopicExchange seckillExchange() {
        return new TopicExchange(SECKILL_EXCHANGE, true, false);
    }

    /**
     * 声明queue
     * @return
     */
    @Bean
    public Queue seckillQueue() {
        return new Queue(SECKILL_QUEUE, true, false, false);
    }

    /**
     * queue 绑定 exchange
     * @return
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(seckillQueue()).to(seckillExchange()).with("seckill.save.order.*");
    }
}
