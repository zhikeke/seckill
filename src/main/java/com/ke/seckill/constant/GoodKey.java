package com.ke.seckill.constant;

import com.ke.seckill.redis.BasePrefix;

public class GoodKey extends BasePrefix {

    private static final int TOKEN_EXPIRE_FOR_HTML = 5;

    private static final int TOKEN_EXPIRE = 4;

    private GoodKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 获取商品列表
     */
    public static GoodKey GET_GOODS_LIST = new GoodKey(TOKEN_EXPIRE, "good_list");

    /**
     * 获取商品列表页面缓存
     */
    public static GoodKey GET_GOODS_LIST_HTML = new GoodKey(TOKEN_EXPIRE_FOR_HTML, "good_list_html");

    /**
     * 商品详情页面缓存
     */
    public static GoodKey GET_GOOD_DETAIL_HTML = new GoodKey(TOKEN_EXPIRE_FOR_HTML, "good_detail_html");

    /**
     * 获取某个商品详细信息
     */
    public static GoodKey GET_GOOD_DETAIL = new GoodKey(TOKEN_EXPIRE, "good_detail");

    /**
     * 秒杀商品库存信息
     */
    public static GoodKey SECKILL_GOODS_STOCK = new GoodKey(0, "seckill_good_stock:");

    /**
     * 秒杀商品开始时间
     */
    public static GoodKey SECKILL_GOODS_START_TIME = new GoodKey(0, "seckill_good_start_time:");

    /**
     * 秒杀商品结束时间
     */
    public static GoodKey SECKILL_GOODS_END_TIME = new GoodKey(0, "seckill_good_end_time:");
}
