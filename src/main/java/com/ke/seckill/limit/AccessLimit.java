package com.ke.seckill.limit;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {
    // 定义时间范围
    int seconds() default 10;

    // 定义访问
    int accessCount() default 5;

    // 是否需要登陆
    boolean needLogin() default true;
}
