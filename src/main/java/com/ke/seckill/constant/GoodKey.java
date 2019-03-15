package com.ke.seckill.constant;

import com.ke.seckill.redis.BasePrefix;

public class GoodKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 5;

    private GoodKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 获取商品列表key
     */
    public static GoodKey GET_GOODS_LIST = new GoodKey(TOKEN_EXPIRE, "good_list");

    public static GoodKey GET_GOOD_DETAIL = new GoodKey(TOKEN_EXPIRE, "good_detail");

}
