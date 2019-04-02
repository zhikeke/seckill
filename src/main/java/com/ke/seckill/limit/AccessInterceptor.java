package com.ke.seckill.limit;

import com.alibaba.fastjson.JSON;
import com.ke.seckill.config.UserLocal;
import com.ke.seckill.constant.AccessKey;
import com.ke.seckill.constant.ConstantKey;
import com.ke.seckill.entity.SeckillUser;
import com.ke.seckill.redis.RedisService;
import com.ke.seckill.response.Response;
import com.ke.seckill.response.ResponseMessage;
import com.ke.seckill.service.ISeckillUserService;
import com.ke.seckill.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AccessInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ISeckillUserService userService;
    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       if (handler instanceof HandlerMethod) {
           HandlerMethod handlerMethod = (HandlerMethod) handler;

           AccessLimit accessLimit = ((HandlerMethod) handler).getMethodAnnotation(AccessLimit.class);

           // 判断是否有 @AccessLimit() 注解
           if (null == accessLimit) {
               return true;
           }

           int seconds = accessLimit.seconds();
           int accessCount = accessLimit.accessCount();
           boolean needLogin = accessLimit.needLogin();

           SeckillUser user = getUser(request, response);

           if (needLogin) {
             if (null == user) {
                 response.setContentType("application/json;charset=UTF-8");
                 String msg = JSON.toJSONString(Response.error(ResponseMessage.SESSION_ERROR));
                 response.getWriter().write(msg);
                 response.flushBuffer();

                 return false;
             }

               UserLocal.setUser(user);
           }

           String key = request.getRequestURI() + "_" + user.getId();
           AccessKey accessKey = AccessKey.ACCESS_PATH_KEY(seconds);

           Integer redisAccessCount = redisService.get(accessKey, key, Integer.class);

           if (null == redisAccessCount) {
               redisService.set(accessKey, key, 1);
           } else if (redisAccessCount < accessCount){
               redisService.incr(accessKey, key);
           } else {
               response.setContentType("application/json;charset=UTF-8");
               String msg = JSON.toJSONString(Response.error(ResponseMessage.ACCESS_LIMIT_REACHED));
               response.getWriter().write(msg);
               response.flushBuffer();

               return false;
           }

       }

       return true;
    }

    private SeckillUser getUser(HttpServletRequest request, HttpServletResponse response) {
        String paramToken = request.getParameter(ConstantKey.COOKIE_NAME_TOKEN);
        String cookieToken = CookieUtil.getCookieValue(request, ConstantKey.COOKIE_NAME_TOKEN);

        String token = StringUtils.isBlank(paramToken) ? cookieToken : paramToken;
        return  userService.getByToken(response, token);
    }
}
