package com.ke.seckill.constant;

import com.ke.seckill.redis.BasePrefix;

public class SeckillUserKey extends BasePrefix {

	public static final int TOKEN_EXPIRE = 3600 * 24 * 2;

	private SeckillUserKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}

	public static SeckillUserKey TOKEN = new SeckillUserKey(TOKEN_EXPIRE, "user_tk");

	public static SeckillUserKey getById = new SeckillUserKey(0, "id");

	/**
	 * 保存用户秒杀路径
	 */
	public static SeckillUserKey SECKILL_PATH = new SeckillUserKey(0, "seckil_path:");

	/**
	 * 保存用户秒杀二维码结果
	 */
	public static SeckillUserKey SECKILL_VERIFY_CODE_RESULT = new SeckillUserKey(300, "seckil_verify_code_result:");
}
