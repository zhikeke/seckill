package com.ke.seckill.service;

import com.ke.seckill.entity.SeckillUser;
import com.baomidou.mybatisplus.service.IService;
import com.ke.seckill.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ma
 * @since 2019-03-07
 */
public interface ISeckillUserService extends IService<SeckillUser> {

    /**
     * 用户登录
     * @param loginVo {@link LoginVo}
     * @return
     */
    boolean login(HttpServletResponse response, LoginVo loginVo);

    /**
     *  根据token 获取用户
     * @param token
     * @return
     */
    SeckillUser getByToken(HttpServletResponse response, String token);
}
