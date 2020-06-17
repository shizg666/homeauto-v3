package com.landleaf.homeauto.common.mqtt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * mqtt配置
 * 
 * @author hebin
 */
@Component
public class MqttConfig {
	/**
	 * mqtt的url
	 */
	@Value("${mqtt.server.url:}")
	private String url;

	/**
	 * mqtt的clientId
	 */
	@Value("${mqtt.server.client-id:}")
	private String clientId;
	

	/**
	 * mqtt的clientId
	 */
	@Value("${mqtt.server.username:}")
	private String username;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
