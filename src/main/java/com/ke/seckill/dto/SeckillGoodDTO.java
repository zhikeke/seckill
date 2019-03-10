package com.ke.seckill.dto;


import com.ke.seckill.entity.SeckillGoods;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 秒杀商品页面数据
 */
@Data
public class SeckillGoodDTO extends SeckillGoods implements Serializable{
    /**
     * 商品id
     */
    private Long goodId;

    /**
     * 商品名称
     */
    private String goodName;

    /**
     * 商品原价格
     */
    private BigDecimal price;;

    /**
     * 商品图片
     */
    private String img;
}
