package com.landleaf.homeauto.common.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Map.Entry;

@Component
@Slf4j
public class MqttFactory {
	@Resource
	private Client asyncClient;

	@Resource
	private Client syncClient;

	@Resource
	private MqttConfig config;

	/**
	 * 内部实例化client
	 */
	private Client myClient;

	private static String THREAD_NAME_MQTT_PING_PREFIX = "MQTT Ping: ";

	private static String THREAD_NAME_MQTT_CALL_PREFIX = "MQTT Call: ";

	private static String THREAD_NAME_MQTT_SEND_PREFIX = "MQTT Snd: ";

	private static String THREAD_NAME_MQTT_RECEIVE_PREFIX = "MQTT Rec: ";

	/**
	 * 获取client，任一个项目只能允许初始化一个client
	 * 
	 * @param async
	 * @return
	 */
	public synchronized Client getClient(boolean async) {
		if (null != myClient) {
			return myClient;
		}
		if (async) {
			myClient = asyncClient;
		} else {
			myClient = syncClient;
		}
		myClient.init(config.getClientId(), config.getUrl(), config.getUsername());
		return myClient;
	}

	/**
	 * 获取client，任一个项目只能允许初始化一个client
	 * 
	 * @param async
	 * @return
	 */
	public synchronized Client reconnect(boolean async) {
		if (null != myClient) {
			myClient.closeConnect();

			// 把mqtt相关的进程杀了
			Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
			String threadName = null;
			for (Entry<Thread, StackTraceElement[]> entry : map.entrySet()) {
				threadName = entry.getKey().getName();
				// 如果是mqtt的对应的线程。直接杀了
				if (null != threadName && (threadName.startsWith(THREAD_NAME_MQTT_PING_PREFIX)
						|| threadName.startsWith(THREAD_NAME_MQTT_CALL_PREFIX)
						|| threadName.startsWith(THREAD_NAME_MQTT_SEND_PREFIX)
						|| threadName.startsWith(THREAD_NAME_MQTT_RECEIVE_PREFIX))) {
					try {
						entry.getKey().stop();
					} catch (Exception e) {
						// 啥都不做
						log.error("杀死线程失败：线程名为{}", threadName);
					}
				}
			}
		}
		if (async) {
			myClient = asyncClient;
		} else {
			myClient = syncClient;
		}
		myClient.init(config.getClientId(), config.getUrl(), config.getUsername());
		return myClient;
	}
}
