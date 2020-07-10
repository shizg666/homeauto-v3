package com.landleaf.homeauto.common.constance;

/**
 * 定义日期格式
 * 
 */
public interface DateFormatConst {
	/**
	 * date转为时间戳
	 */
	String TIMESTAMP = "timestamp";
	
	/**
	 * date转为unix时间戳 
	 */
	String UNIX_TIMESTAMP = "unix";
	
	/**
	 * 日期格式为yyyyMMdd
	 */
	String PATTERN_YYYYMMDD = "yyyyMMdd";
	/**
	 * 日期格式为yyyy.MM.dd
	 */
	String PATTERN_YYYY_SIGN_MM_SIGN_DD = "yyyy.MM.dd";

	/**
	 * 日期格式为yyyyMMddHHmmss
	 */
	String PATTERN_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	/**
	 * 日期格式为yyyy-MM-dd HH:mm:ss
	 */
	String PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 日期格式为yyyy-MM-dd
	 */
	String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";

	/**
	 * 日期格式为yyyy/MM/dd
	 */
	String PATTERN_YYYYBSMMBSDD = "yyyy/MM/dd";
	
	/**
	 * 日期格式为MM/dd
	 */
	String PATTERN_MMBSDD = "MM/dd";

	/**
	 * 日期格式为yyyy-MM
	 */
	String PATTERN_YYYY_MM = "yyyy-MM";

	/**
	 * 转化为date 日期格式为yyyy-MM-dd
	 */
	String[] PATTERN_DATE_YYYY_MM_DD = { "yyyy-MM-dd" };

	/**
	 * 转化为date 日期格式为yyyy-MM-dd
	 */
	String[] PATTERN_DATE_YYYY_MM_DD_HH_MM_SS = { "yyyy-MM-dd HH:mm:ss" };

	/**
	 * 转化为date 日期格式为yyyyMMdd
	 */
	String[] PATTERN_DATE_YYYYMMDD = { "yyyyMMdd" };
}
