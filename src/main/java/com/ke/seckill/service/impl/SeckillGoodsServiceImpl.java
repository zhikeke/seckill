package com.ke.seckill.service.impl;

import com.ke.seckill.dto.SeckillGoodDTO;
import com.ke.seckill.entity.SeckillGoods;
import com.ke.seckill.entity.SeckillOrders;
import com.ke.seckill.mapper.SeckillGoodsMapper;
import com.ke.seckill.service.ISeckillGoodsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public List<SeckillGoodDTO> getAll() {
        return seckillGoodsMapper.findAll();
    }

    @Override
    public SeckillGoodDTO getDetailById(long goodId) {
        return seckillGoodsMapper.getDetailById(goodId);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reduceStock(SeckillGoodDTO good) {
        seckillGoodsMapper.reduceStock(good.getGoodId());
    }


}
