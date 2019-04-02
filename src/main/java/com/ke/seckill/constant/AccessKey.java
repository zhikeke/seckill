package com.ke.seckill.constant;

import com.ke.seckill.redis.BasePrefix;

public class AccessKey extends BasePrefix {

    private AccessKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }


    public static AccessKey DEFAULT_ACCESS_PATH_KEY = new AccessKey(5,"access_path_limint:");

    public static AccessKey ACCESS_PATH_KEY(int expireSeconds) {
        return new AccessKey(expireSeconds, "access_path_limint:");
    }
}
