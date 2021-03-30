package com.landleaf.homeauto.mqtt.transport.server.handler.sub;

import com.landleaf.homeauto.mqtt.api.MqttMessageApi;
import com.landleaf.homeauto.mqtt.api.RsocketConfiguration;
import com.landleaf.homeauto.mqtt.api.RsocketTopicManager;
import com.landleaf.homeauto.mqtt.api.TransportConnection;
import com.landleaf.homeauto.mqtt.config.RsocketServerConfig;
import com.landleaf.homeauto.mqtt.transport.DirectHandler;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.mqtt.*;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class SubHandler implements DirectHandler {


    @Override
    public void handler(MqttMessage message, TransportConnection connection, RsocketConfiguration config) {
            RsocketServerConfig serverConfig = (RsocketServerConfig) config;
            MqttFixedHeader header = message.fixedHeader();
            switch (header.messageType()) {
                case SUBSCRIBE:
                    MqttSubscribeMessage subscribeMessage = (MqttSubscribeMessage) message;
                    int messageId = subscribeMessage.variableHeader().messageId();
                    List<Integer> grantedQoSLevels = subscribeMessage.payload().topicSubscriptions().stream().map(m -> m.qualityOfService().value()).collect(Collectors.toList());
                    MqttSubAckMessage mqttSubAckMessage = MqttMessageApi.buildSubAck(messageId, grantedQoSLevels);
                    connection.write(mqttSubAckMessage).subscribe();
                    RsocketTopicManager topicManager = serverConfig.getTopicManager();
                    subscribeMessage.payload().topicSubscriptions().stream().forEach(m -> {
                        String topic = m.topicName();
                        topicManager.addTopicConnection(topic, connection);
                        serverConfig.getMessageHandler().getRetain(topic)
                                .ifPresent(messages->{
                                    if (messages.getQos() == 0) {
                                        connection.write(MqttMessageApi.buildPub(messages.isDup(), MqttQoS.valueOf(messages.getQos()), messages.isRetain(),1, messages.getTopicName(), Unpooled.wrappedBuffer(messages.getCopyByteBuf()))).subscribe();
                                    } else {
                                        int id = connection.messageId();
                                        connection.addDisposable(id, Mono.fromRunnable(() ->
                                                connection.write(MqttMessageApi.buildPub(true, header.qosLevel(), header.isRetain(), id, messages.getTopicName(), Unpooled.wrappedBuffer(messages.getCopyByteBuf()))).subscribe())
                                                .delaySubscription(Duration.ofSeconds(10)).repeat().subscribe()); // retry
                                        MqttPublishMessage publishMessage = MqttMessageApi.buildPub(false, header.qosLevel(), header.isRetain(), id, messages.getTopicName(), Unpooled.wrappedBuffer(messages.getCopyByteBuf())); // pub
                                        connection.write(publishMessage).subscribe();
                                    }
                                });
                    });

                    break;
                case UNSUBSCRIBE:
                    MqttUnsubscribeMessage mqttUnsubscribeMessage = (MqttUnsubscribeMessage) message;
                    MqttUnsubAckMessage mqttUnsubAckMessage = MqttMessageApi.buildUnsubAck(mqttUnsubscribeMessage.variableHeader().messageId());
                    connection.write(mqttUnsubAckMessage).subscribe();
                    mqttUnsubscribeMessage.payload().topics().stream().forEach(m -> serverConfig.getTopicManager().deleteTopicConnection(m, connection));
                    break;
            }
    }
}
