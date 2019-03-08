package com.ke.seckill.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *  用户表
 * </p>
 *
 * @author keke
 * @since 2019-03-07
 */
@Data
@TableName("seckill_user")
public class SeckillUser extends Model<SeckillUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * MD5(MD5(pass明文+固定salt) + 随机salt)
     */
    private String password;

    private String salt;
    /**
     * 头像 云存储ID
     */
    private String head;
    /**
     * 注册时间
     */
    @TableField("register_date")
    private Date registerDate;
    /**
     * 上次登录时间
     */
    @TableField("last_login_date")
    private Date lastLoginDate;
    /**
     * 登录次数
     */
    @TableField("login_count")
    private Integer loginCount;

    /**
     * 是否删除 0：未删除 1:已删除
     */
    @TableField("deleted")
    private Integer deleted;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }


}
