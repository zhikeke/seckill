package com.ke.seckill.init;

import com.ke.seckill.constant.GoodKey;
import com.ke.seckill.dto.SeckillGoodDTO;
import com.ke.seckill.redis.RedisService;
import com.ke.seckill.service.ISeckillGoodsService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 系统初始化完成后，把商品库存信息保存到redis中
 */
@Component
public class LoadSeckillGoodToRedis implements InitializingBean {
    @Autowired
    private ISeckillGoodsService seckillGoodsService;
    @Autowired
    private RedisService redisService;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<SeckillGoodDTO> goods = seckillGoodsService.getAll();

        if (null != goods && goods.size() > 0) {
            for (SeckillGoodDTO good : goods) {
                // 设置秒杀库存
                redisService.set(GoodKey.SECKILL_GOODS_STOCK, String.valueOf(good.getGoodId()), good.getStock());

                // 设置秒杀开始时间
                redisService.set(GoodKey.SECKILL_GOODS_START_TIME, String.valueOf(good.getGoodId()), good.getStartDate().getTime());

                // 设置秒杀结束时间
                redisService.set(GoodKey.SECKILL_GOODS_END_TIME, String.valueOf(good.getGoodId()), good.getEndDate().getTime());
            }
        }
    }
}
