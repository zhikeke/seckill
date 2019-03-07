package com.ke.seckill.util;

import org.apache.commons.codec.digest.DigestUtils;


public class MD5Util {
	private static final String SALT = "seckill888";
	
	public static String md5(String src) {
		return DigestUtils.md5Hex(src);
	}

    /**
     * 用户数据密码转为表单密码
     * @param inputPass
     * @return
     */
	public static String inputPassToFormPass(String inputPass) {
		String str = "" + SALT.charAt(0) + SALT.charAt(2) + inputPass + SALT.charAt(5) + SALT.charAt(4);
		return md5(str);
	}

    /**
     * 表单密码转为数据库保存密码
     * @param formPass
     * @param salt
     * @return
     */
	public static String formPassToDBPass(String formPass, String salt) {
		String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
		return md5(str);
	}

    /**
     * 用户数据密码转为数据库保存密码
     * @param inputPass
     * @param saltDB
     * @return
     */
	public static String inputPassToDbPass(String inputPass, String saltDB) {
		String formPass = inputPassToFormPass(inputPass);
		String dbPass = formPassToDBPass(formPass, saltDB);
		return dbPass;
	}

}
