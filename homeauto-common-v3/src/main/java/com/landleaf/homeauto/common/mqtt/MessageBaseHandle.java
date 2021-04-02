package com.landleaf.homeauto.common.mqtt;

import com.alibaba.fastjson.JSON;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.lang.reflect.ParameterizedType;

/**
 * 抛出abs类，提供handle方法供使用方继承使用
 *
 * @author wenyilu
 */
public abstract class MessageBaseHandle<T> {

    private Class<T> clazz;

    /**
     * 处理逻辑
     *
     * @param topic   topic的名称
     * @param message message的内容
     */
    public abstract void handle(String topic, MqttMessage message);

    public T convert(String msg) {
        ParameterizedType type = (ParameterizedType) this.getClass()
                .getGenericSuperclass();
        this.clazz = (Class<T>) type.getActualTypeArguments()[0];
        return JSON.parseObject(msg, clazz);
    }


}
