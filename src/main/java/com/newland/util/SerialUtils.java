package com.newland.util;

import java.util.UUID;

import org.apache.commons.lang3.RandomUtils;

/**
 * 序号生成工具类
 * 
 * @author: fangxu.ge
 * 
 */
public class SerialUtils {

	/**
	 * 生成26位序列号
	 * 生成方式：年月日时分秒毫秒的时间戳+九位随机数
	 */
	public static String getSerialNo() {
		return DateUtils.getStringDateYMDHMSS() + RandomUtils.nextInt(100000000, 999999999);
	}
	
	/**
	 * 生成32位UUID
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}
	
	/**
	 * 生成数据库主键
	 * 例:当前时间20170607101133+三位毫秒值953+5位随机数56234
	 * @return
	 */
	public static String getPrimaryKey(){
		return DateUtils.getStringDateYMDHMSS() + RandomUtils.nextInt(10000, 99999);
	}
	/**
	 * 生成19位序列号
	 * @return
	 */
	public static String get19SerialNo() {
		return DateUtils.getStringDateYMDHMS() + RandomUtils.nextInt(10000, 99999);
	}
}