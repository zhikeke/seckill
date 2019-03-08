package com.ke.seckill.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ma
 * @since 2019-03-08
 */
public class Goods extends Model<Goods> {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    private Long id;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品标题
     */
    private String title;
    /**
     * 商品封面
     */
    private String img;
    /**
     *  商品详情
     */
    private String detail;
    /**
     * 商品价格
     */
    private BigDecimal price;
    /**
     * 商品库存
     */
    private Integer stock;
    /**
     * 创建时间
     */
    @TableField("gmt_create")
    private Date gmtCreate;
    /**
     *  修改时间
     */
    @TableField("gmt_modified")
    private Date gmtModified;
    /**
     * 是否删除 0：未删除 1:已删除
     */
    private Integer deleted;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Goods{" +
        "id=" + id +
        ", name=" + name +
        ", title=" + title +
        ", img=" + img +
        ", detail=" + detail +
        ", price=" + price +
        ", stock=" + stock +
        ", gmtCreate=" + gmtCreate +
        ", gmtModified=" + gmtModified +
        ", deleted=" + deleted +
        "}";
    }
}
