package com.ke.seckill.config;

import com.ke.seckill.constant.ConstantKey;
import com.ke.seckill.entity.SeckillUser;
import com.ke.seckill.service.ISeckillUserService;
import com.ke.seckill.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private ISeckillUserService userService;

    /**
     * 判断当前参数类型是否匹配，匹配则执行 resolveArgument 方法
     * @param methodParameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();

        return clazz == SeckillUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {

        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);

        String paramToken = request.getParameter(ConstantKey.COOKIE_NAME_TOKEN);
        String cookieToken = CookieUtil.getCookieValue(request, ConstantKey.COOKIE_NAME_TOKEN);

        String token = StringUtils.isBlank(paramToken) ? cookieToken : paramToken;

        return userService.getByToken(response, token);
    }
}
