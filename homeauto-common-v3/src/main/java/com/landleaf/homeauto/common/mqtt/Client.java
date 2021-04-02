package com.landleaf.homeauto.common.mqtt;

import com.landleaf.homeauto.common.constant.enums.QosEnumConst;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * mqtt客户端
 */
@Data
public abstract class Client {

    protected MqttReceriveCallback mqttReceriveCallback;

    protected MqttConfigProperty mqttConfigProperty;


    /**
     * 持久化方式
     */
    protected MemoryPersistence memoryPersistence = null;

    /**
     * 链接选项
     */
    protected MqttConnectOptions mqttConnectOptions = null;

    /**
     * 初始化mqtt链接
     */
    public abstract void init();

    /**
     * 默认配置
     */
    public MqttConnectOptions defaultMqttConnectOptions(MqttConfigProperty mqttConfigProperty) {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        // 初始化MqttClient
        // true可以安全地使用内存持久性作为客户端断开连接时清除的所有状态
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setAutomaticReconnect(false);
        // 设置连接超时
        mqttConnectOptions.setConnectionTimeout(30);
        // 设置最大链接数
        mqttConnectOptions.setMaxInflight(10000);
        String serverUserName = mqttConfigProperty.getServerUserName();
        String serverPassword = mqttConfigProperty.getServerPassword();
        if (!StringUtils.isEmpty(serverUserName)) {
            mqttConnectOptions.setUserName(serverUserName);
        }
        if (!StringUtils.isEmpty(serverPassword)) {
            mqttConnectOptions.setPassword(serverPassword.toCharArray());
        }
        mqttConnectOptions.setHttpsHostnameVerificationEnabled(false);
        mqttConnectOptions.setSSLHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });
        mqttConnectOptions.setCleanSession(false);
        return mqttConnectOptions;
    }

    /**
     * 发布信息
     *
     * @param topic   topic名称，不支持通配符
     * @param message 消息
     * @param qos     qos级别
     */
    public abstract void pubTopic(String topic, String message, QosEnumConst qos);

    /**
     * 订阅消息
     *
     * @param topic topic名称，支持统配
     */
    public abstract void subTopic(String topic);

    /**
     * 关闭链接
     */
    abstract void closeConnect();
}
