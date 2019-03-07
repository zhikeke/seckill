package com.ke.seckill.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest; /**
 * 操作cookie 工具类
 */
public class CookieUtil {

    /**
     * 根据cookie key 获取值
     * @param request
     * @param cookieKey
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieKey) {
        Cookie[] cookies = request.getCookies();

        if (null != cookies && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookieKey.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
