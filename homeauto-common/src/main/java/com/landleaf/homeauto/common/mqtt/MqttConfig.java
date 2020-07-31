package com.landleaf.homeauto.common.mqtt;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * mqtt 配置
 */

@ConditionalOnProperty(prefix = "homeauto.mqtt", name = "enable")
@Component
public class MqttConfig {


//    @Bean
//    public DefaultMessageHandle defaultMessageHandle() {
//        return new DefaultMessageHandle();
//    }


    @Bean
    public MqttReceriveCallback mqttReceriveCallback(List<MessageBaseHandle> handleList, DefaultMessageHandle defaultMessageHandle,
                                                     Executor receiveExecutor) {
        MqttReceriveCallback mqttReceriveCallback = new MqttReceriveCallback();
        mqttReceriveCallback.postMethod(handleList, defaultMessageHandle, receiveExecutor);
        return mqttReceriveCallback;
    }

    @Bean
    public AsyncClient asyncClient(MqttReceriveCallback mqttReceriveCallback, MqttConfigProperty mqttConfigProperty) {
        AsyncClient asyncClient = new AsyncClient();
        asyncClient.setMqttReceriveCallback(mqttReceriveCallback);
        return asyncClient;
    }

    @Bean
    public SyncClient syncClient(MqttReceriveCallback mqttReceriveCallback) {
        SyncClient syncClient = new SyncClient();
        syncClient.setMqttReceriveCallback(mqttReceriveCallback);
        return syncClient;
    }

    @Bean
    public MqttFactory mqttFactory(AsyncClient asyncClient, SyncClient syncClient, MqttConfigProperty mqttConfigProperty) {
        MqttFactory mqttFactory = new MqttFactory();
        mqttFactory.build(asyncClient, syncClient, mqttConfigProperty);
        return mqttFactory;
    }

    @Bean
    public NotifyBaseService notifyBaseService(MqttFactory mqttFactory) {

        NotifyBaseService notifyBaseService = new NotifyBaseService();
        notifyBaseService.setMqttFactory(mqttFactory);

        return notifyBaseService;
    }

    @Bean
    public RetrySubTopic retrySubTopic(MqttFactory mqttFactory) {

        RetrySubTopic retrySubTopic = new RetrySubTopic();
        retrySubTopic.setMqttFactory(mqttFactory);

        return retrySubTopic;
    }

    @Bean
    public SyncSendUtil syncSendUtil(MqttConfigProperty mqttConfigProperty, Environment environment) {

        SyncSendUtil syncSendUtil = new SyncSendUtil();
        syncSendUtil.setMqttConfigProperty(mqttConfigProperty);
        syncSendUtil.setEnvironment(environment);
        syncSendUtil.init();
        return syncSendUtil;
    }


}
