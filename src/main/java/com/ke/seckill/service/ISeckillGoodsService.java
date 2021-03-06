package com.ke.seckill.service;

import com.ke.seckill.dto.SeckillGoodDTO;
import com.ke.seckill.entity.SeckillGoods;
import com.baomidou.mybatisplus.service.IService;
import com.ke.seckill.entity.SeckillOrders;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author keke
 * @since 2019-03-10
 */
public interface ISeckillGoodsService extends IService<SeckillGoods> {

    /**
     * 查询所有秒杀商品列表
     * @return
     */
    List<SeckillGoodDTO> getAll();

    /**
     * 根据商品id获取商品详情
     * @param goodId
     * @return
     */
    SeckillGoodDTO getDetailById(long goodId);

    /**
     * 减少库存
     * @param good
     */
    int reduceStock(SeckillGoodDTO good);

    /**
     * 减少库存
     * @param goodId 商品id
     * @return
     */
    int reduceStock(long goodId);

    /**
     * 删除数据库中是否还有商品库存
     * @param goodId 商品库存
     * @return
     */
    boolean getStockById(long goodId);
}
