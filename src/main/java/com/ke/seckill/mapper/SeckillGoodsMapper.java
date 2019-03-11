package com.ke.seckill.mapper;

import com.ke.seckill.dto.SeckillGoodDTO;
import com.ke.seckill.entity.SeckillGoods;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author keke
 * @since 2019-03-10
 */
public interface SeckillGoodsMapper extends BaseMapper<SeckillGoods> {
    /**
     * 查询所有秒杀商品列表
     * @return
     */
    List<SeckillGoodDTO> findAll();

    /**
     * 根据商品id获取商品详情
     * @param goodId 商品id
     * @return
     */
    SeckillGoodDTO getDetailById(@Param("goodId") long goodId);

    /**
     *更新秒杀商品库存
     * @param goodId 商品ID
     * @param stock 库存
     */
    void reduceStock(@Param("goodId") Long goodId, @Param("stock") Integer stock);
}
