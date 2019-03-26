package com.ke.seckill.constant;

import com.ke.seckill.redis.BasePrefix;

public class SeckillOrderKey extends BasePrefix {

	private SeckillOrderKey(String prefix) {
		super(prefix);
	}

	public static SeckillOrderKey getSeckillOrderByUidAndGid = new SeckillOrderKey("seckill_order");
}
