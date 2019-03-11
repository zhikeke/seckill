package com.ke.seckill.service;

import com.ke.seckill.dto.SeckillGoodDTO;
import com.ke.seckill.entity.Orders;
import com.ke.seckill.entity.SeckillUser; /**
 * 秒杀服务接口类
 */
public interface ISeckillService {
    /**
     * 秒杀
     * @param user
     * @param good
     * @return
     */
    Orders seckill(SeckillUser user, SeckillGoodDTO good);
}
