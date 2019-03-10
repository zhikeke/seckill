package com.ke.seckill.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author keke
 * @since 2019-03-10
 */
public class Orders extends Model<Orders> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 商品id
     */
    @TableField("good_id")
    private Long goodId;
    /**
     * 地址id
     */
    @TableField("delivery_addr_id")
    private Long deliveryAddrId;
    /**
     * 商品名字
     */
    @TableField("good_name")
    private String goodName;
    /**
     * 购买数量
     */
    @TableField("good_count")
    private Integer goodCount;
    /**
     * 商品价格
     */
    @TableField("good_price")
    private BigDecimal goodPrice;
    /**
     * 下单渠道 1pc 2android 3ios
     */
    @TableField("order_channel")
    private Integer orderChannel;
    /**
     * 订单状态 0：新建未支付 1：已支付 2：已发货 3：已收货
     */
    private Integer status;
    /**
     * 创建时间
     */
    @TableField("create_date")
    private Date createDate;
    /**
     * 支付时间
     */
    @TableField("pay_date")
    private Date payDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public Long getDeliveryAddrId() {
        return deliveryAddrId;
    }

    public void setDeliveryAddrId(Long deliveryAddrId) {
        this.deliveryAddrId = deliveryAddrId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public Integer getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(Integer goodCount) {
        this.goodCount = goodCount;
    }

    public BigDecimal getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(BigDecimal goodPrice) {
        this.goodPrice = goodPrice;
    }

    public Integer getOrderChannel() {
        return orderChannel;
    }

    public void setOrderChannel(Integer orderChannel) {
        this.orderChannel = orderChannel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Orders{" +
        "id=" + id +
        ", userId=" + userId +
        ", goodId=" + goodId +
        ", deliveryAddrId=" + deliveryAddrId +
        ", goodName=" + goodName +
        ", goodCount=" + goodCount +
        ", goodPrice=" + goodPrice +
        ", orderChannel=" + orderChannel +
        ", status=" + status +
        ", createDate=" + createDate +
        ", payDate=" + payDate +
        "}";
    }
}
