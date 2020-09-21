package com.landleaf.homeauto.common.mqtt;

import com.landleaf.homeauto.common.constant.enums.QosEnumConst;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SyncClient extends Client {

    private static Logger logger = LoggerFactory.getLogger(SyncClient.class);

    public static MqttClient mqttClient = null;


    @Override
    public void init() {

        // 初始化连接设置对象
        // 初始化连接设置对象
        mqttConnectOptions = defaultMqttConnectOptions(mqttConfigProperty);
        // 设置ssl
        try {
//            mqttConnectOptions.setSocketFactory(MqttSslUtil.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 设置持久化方式
        memoryPersistence = new MemoryPersistence();

        try {
            mqttClient = new MqttClient(mqttConfigProperty.getServerUrl(), mqttConfigProperty.getServerClientId(), memoryPersistence);
        } catch (MqttException e) {
            logger.error("初始化mqtt客户端失败.", e);
        }

        // 设置连接和回调
        if (null != mqttClient) {
            if (!mqttClient.isConnected()) {
                // 客户端添加回调函数
                mqttClient.setCallback(mqttReceriveCallback);
                // 创建连接
                try {
                    logger.info("开始创建mqtt链接.");
                    mqttClient.connect(mqttConnectOptions);
                } catch (MqttException e) {
                    logger.error("创建mqtt链接失败.", e);
                }
            }
        } else {
            logger.info("初始化mqtt客户端失败.");
        }
    }

    // 关闭连接
    @Override
    public void closeConnect() {
        closeMemoryPersistence();

        closeMqttClient();
    }

    private void closeMemoryPersistence() {
        // 关闭存储方式
        if (null != memoryPersistence) {
            try {
                memoryPersistence.close();
            } catch (MqttPersistenceException e) {
                logger.error("Close memoryPersistence error.", e);
            }
        }
    }

    private void closeMqttClient() {
        if (null != mqttClient) {
            if (mqttClient.isConnected()) {
                try {
                    mqttClient.disconnect();
                } catch (MqttException e) {
                    logger.error("Close mqttClient error.", e);
                }
            } else {
                logger.info("mqttClient is not connect");
            }
            try {
                mqttClient.close();
            } catch (MqttException e) {
                logger.error("Close mqttClient error.", e);
            }
        }
    }

    // 发布消息
    @Override
    public void pubTopic(String pubTopic, String message, QosEnumConst qos) {
        if (null != mqttClient && mqttClient.isConnected()) {
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setQos(qos.getQosCode());
            mqttMessage.setPayload(message.getBytes());

            try {
                mqttClient.publish(pubTopic, mqttMessage);
            } catch (MqttException e) {
                logger.error("发布mqtt消息失败.", e);
            }

        } else {
            reConnect();
        }

    }

    // 重新连接
    private void reConnect() {
        if (null != mqttClient) {
            if (!mqttClient.isConnected()) {
                if (null != mqttConnectOptions) {
                    try {
                        mqttClient.connect(mqttConnectOptions);
                    } catch (MqttException e) {
                        logger.error("重连mqtt消息失败.", e);
                    }
                } else {
                    logger.info("mqttConnectOptions is null");
                }
            } else {
                logger.info("mqttClient is null or connect");
            }
        } else {
            init();
        }
    }

    // 订阅主题
    @Override
    public void subTopic(String topic) {
        if (null != mqttClient && mqttClient.isConnected()) {
            try {
                logger.info("开始订阅topic:{}", topic);
                mqttClient.subscribe(topic, 1);
                logger.info("订阅topic:{}成功", topic);
            } catch (MqttException e) {
                logger.error("订阅mqtt消息失败----报错,:{}", e.getMessage());
            }
        } else {
            logger.error("订阅mqtt消息失败");
        }
    }

}
