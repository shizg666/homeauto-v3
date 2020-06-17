package com.landleaf.homeauto.common.util;

import java.util.UUID;

/**
 * 生成id的工具类
 * 
 * @author hebin
 */
public final class IdGeneratorUtil {

	/**
	 * 获取32位uuid
	 * 
	 * @return 32位uuid
	 */
	public static String getUUID32() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
