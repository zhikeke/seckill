package com.ke.seckill.service.impl;

import com.ke.seckill.constant.ConstantKey;
import com.ke.seckill.entity.SeckillUser;
import com.ke.seckill.exception.GlobalException;
import com.ke.seckill.mapper.SeckillUserMapper;
import com.ke.seckill.redis.RedisService;
import com.ke.seckill.redis.SeckillUserKey;
import com.ke.seckill.response.ResponseMessage;
import com.ke.seckill.service.ISeckillUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ke.seckill.util.MD5Util;
import com.ke.seckill.util.RandomUtil;
import com.ke.seckill.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author keke
 * @since 2019-03-07
 */
@Service
public class SeckillUserServiceImpl extends ServiceImpl<SeckillUserMapper, SeckillUser> implements ISeckillUserService {

    @Autowired
    private SeckillUserMapper userMapper;
    @Autowired
    private RedisService redisService;


    /**
     * 用户登录
     * @param loginVo {@link LoginVo}
     * @return
     */
    @Override
    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if (null == loginVo) {
            throw new GlobalException(ResponseMessage.SERVER_ERROR);
        }

        SeckillUser user = userMapper.selectByNickName(loginVo.getNickname());

        if (null == user) {
            throw new GlobalException(ResponseMessage.NICKNAME_NOT_EXIST);
        }

        String password = MD5Util.formPassToDBPass(loginVo.getPassword(), user.getSalt());

        if (!password.equals(user.getPassword())) {
            throw new GlobalException(ResponseMessage.PASSWORD_ERROR);
        }

        // TODO: kafka 保存用户登录信息

        // 生成cookie
        addCookie(response, user);

        return true;
    }

    /**
     * 根据token 获取用户
     * @param token
     * @return
     */
    @Override
    public SeckillUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }

        SeckillUser user = redisService.get(SeckillUserKey.TOKEN, token, SeckillUser.class);
        // 延长cookie时间
        addCookie(response, user);

        return user;
    }


    private void addCookie(HttpServletResponse response, SeckillUser user) {
        String token = RandomUtil.getRandomString(20);
        user.setPassword(null);
        redisService.set(SeckillUserKey.TOKEN, token, user);

        Cookie cookie = new Cookie(ConstantKey.COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(SeckillUserKey.TOKEN_EXPIRE);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
