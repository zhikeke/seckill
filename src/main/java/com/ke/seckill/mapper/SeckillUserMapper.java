package com.ke.seckill.mapper;

import com.ke.seckill.entity.SeckillUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author keke
 * @since 2019-03-07
 */
public interface SeckillUserMapper extends BaseMapper<SeckillUser> {

    /**
     * 根据用户名查询用户
     * @param nickname 用户名
     * @return
     */
    SeckillUser selectByNickName(@Param("nickname") String nickname);
}
