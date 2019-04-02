package com.ke.seckill.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * RabbitMQ 保存的下订单消息所需内容
 */
@Data
public class OrderMessage implements Serializable {
    /**
     * 商品id
     */
    private long goodId;

    /**
     * 用户id
     */
    private long userId;

}
