package com.ke.seckill.util;

import java.util.Random;

public class RandomUtil {

	public static final String ALL_CHAR = "-_#&$@+-*/%()[]0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static final String LETTER_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static final String NUMBER_CHAR = "0123456789";

	public static final String SPECIAL_CHAR = "-_#&$@+-*/%()[]";

	public static final String LETTER_NUMBER_CHAR = LETTER_CHAR + NUMBER_CHAR;

	/**
	 * 返回一个定长的随机字符串
	 *
	 * @param chars
	 *            模型串
	 * @param length
	 *            随机长度
	 * @return
	 */
	public static String randomString(String chars, int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(chars.charAt(random.nextInt(chars.length())));
		}
		return sb.toString();
	}

	/**
	 * 返回一个定长的随机字符串字母全部大写
	 *
	 * @param chars
	 *            模型串
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String randomLowerString(String chars, int length) {
		return randomString(chars, length).toLowerCase();
	}

	/**
	 * 返回一个定长的随机字符串字母全部小写
	 *
	 * @param chars
	 *            模型串
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String randomUpperString(String chars, int length) {
		return randomString(chars, length).toLowerCase();
	}

	/**
	 * 生成一个定长的纯0字符串
	 *
	 * @param length
	 *            字符串长度
	 * @return 纯0字符串
	 */
	public static String randomZeroString(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append("0");
		}
		return sb.toString();
	}

	//获取指定位数的随机字符串(包含小写字母、大写字母、数字,0<length)
	public static String getRandomString(int length) {
		//随机字符串的随机字符库
		String KeyString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuffer sb = new StringBuffer();
		int len = KeyString.length();
		for (int i = 0; i < length; i++) {
			sb.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
		}
		return sb.toString();
	}
}
