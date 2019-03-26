package com.ke.seckill.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单详情DTO
 */
@Data
public class OrderDetailVO implements Serializable {

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 商品名称
     */
    private String goodName;

    /**
     * 商品名称
     */
    private String goodImg;

    /**
     * 订单价格
     */
    private String orderPrice;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 订单状态
     */
    private String orderStatus;

}
