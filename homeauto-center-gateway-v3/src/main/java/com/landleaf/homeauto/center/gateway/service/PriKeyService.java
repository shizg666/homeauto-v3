package com.landleaf.homeauto.center.gateway.service;

/**
 * 私钥相关权限
 * 
 * @author hebin
 */
public interface PriKeyService {

	/**
	 * 根据appKey,获取私钥
	 * @param appKey appKey
	 * @return 返回私钥
	 */
	String getPriKeyByAppKey(String appKey);
}
