package com.landleaf.homeauto.common.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * 抛出abs类，提供handle方法供使用方继承使用
 * 
 * @author hebin
 */
public abstract class MessageBaseHandle {
	/**
	 * 处理逻辑
	 * 
	 * @param topic
	 *            topic的名称
	 * @param message
	 *            message的内容
	 */
	public abstract void handle(String topic, MqttMessage message);
}
