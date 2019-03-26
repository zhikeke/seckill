package com.ke.seckill.vo;

import com.ke.seckill.dto.SeckillGoodDTO;
import com.ke.seckill.entity.SeckillUser;
import lombok.Data;

import java.io.Serializable;
@Data
public class GoodDetailVO implements Serializable {

    /**
     * 秒杀商品信息
     */
    private SeckillGoodDTO seckillGood;

    /**
     * 当前登陆用户
     */
    private SeckillUser user;

    /**
     * 剩余时间
     */
    private Long remainSeconds;

    /**
     * 秒杀开始状态
     */
    private int seckillStatus;
}
