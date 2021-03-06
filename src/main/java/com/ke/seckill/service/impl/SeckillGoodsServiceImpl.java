package com.ke.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ke.seckill.constant.GoodKey;
import com.ke.seckill.dto.SeckillGoodDTO;
import com.ke.seckill.entity.SeckillGoods;
import com.ke.seckill.entity.SeckillOrders;
import com.ke.seckill.mapper.SeckillGoodsMapper;
import com.ke.seckill.redis.RedisService;
import com.ke.seckill.service.ISeckillGoodsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author keke
 * @since 2019-03-10
 */
@Service
public class SeckillGoodsServiceImpl extends ServiceImpl<SeckillGoodsMapper, SeckillGoods> implements ISeckillGoodsService {

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;
    @Autowired
    private RedisService redisService;

    @Override
    public List<SeckillGoodDTO> getAll() {
        List<SeckillGoodDTO> seckillGoodDTOS = new ArrayList<>();
        Object seckillGoods = redisService.get(GoodKey.GET_GOODS_LIST, null, String.class);

        if (null != seckillGoods) {
            Object listInRedis = JSONObject.parseArray(seckillGoods.toString(), SeckillGoodDTO.class);

            if (listInRedis instanceof List) {
                seckillGoodDTOS = (List<SeckillGoodDTO>) listInRedis;
            }

            return seckillGoodDTOS;
        }

        seckillGoodDTOS = seckillGoodsMapper.findAll();

        redisService.set(GoodKey.GET_GOODS_LIST, null, JSONObject.toJSONString(seckillGoodDTOS));

        return seckillGoodDTOS;
    }

    /**
     * 只从数据库读取
     * @param goodId
     * @return
     */
    @Override
    public SeckillGoodDTO getDetailById(long goodId) {
        return seckillGoodsMapper.getDetailById(goodId);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int reduceStock(SeckillGoodDTO good) {
        return seckillGoodsMapper.reduceStock(good.getGoodId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int reduceStock(long goodId) {
        return seckillGoodsMapper.reduceStock(goodId);
    }

    @Override
    public boolean getStockById(long goodId) {
        int stock = seckillGoodsMapper.getStockById(goodId);

        return stock > 0;
    }


}
