package com.landleaf.homeauto.contact.screen.service;

/**
 * 查看mqtt链接是否无误的逻辑句柄
 * 
 */
public interface MqttConnCheckService {
	/**
	 * 查看链接是否ok
	 */
	boolean checkLink();

	/**
	 * 更新修改时间
	 */
	void refresh();

}
