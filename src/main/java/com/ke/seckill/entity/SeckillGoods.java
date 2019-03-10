package com.ke.seckill.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author keke
 * @since 2019-03-10
 */
@TableName("seckill_goods")
public class SeckillGoods extends Model<SeckillGoods> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 商品id
     */
    @TableField("good_id")
    private Long goodId;
    /**
     * 商品价格
     */
    @TableField("seckill_price")
    private BigDecimal seckillPrice;
    /**
     * 库存
     */
    private Integer stock;
    /**
     * 秒杀开始时间
     */
    @TableField("start_date")
    private Date startDate;
    /**
     * 秒杀结束时间
     */
    @TableField("end_date")
    private Date endDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public BigDecimal getSeckillPrice() {
        return seckillPrice;
    }

    public void setSeckillPrice(BigDecimal seckillPrice) {
        this.seckillPrice = seckillPrice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SeckillGoods{" +
        "id=" + id +
        ", goodId=" + goodId +
        ", seckillPrice=" + seckillPrice +
        ", stock=" + stock +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        "}";
    }
}
