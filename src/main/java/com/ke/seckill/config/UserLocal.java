package com.ke.seckill.config;

import com.ke.seckill.entity.SeckillUser;

public class UserLocal {
    private static java.lang.ThreadLocal<SeckillUser> userLocal = new java.lang.ThreadLocal();

    public static void setUser(SeckillUser user) {
        userLocal.set(user);
    }

    public static SeckillUser getCurrentUser() {
        return userLocal.get();
    }
}
