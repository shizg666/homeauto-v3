package com.landleaf.homeauto.mqtt.transport.client.handler.pub;

import com.landleaf.homeauto.mqtt.api.MqttMessageApi;
import com.landleaf.homeauto.mqtt.api.RsocketConfiguration;
import com.landleaf.homeauto.mqtt.api.TransportConnection;
import com.landleaf.homeauto.mqtt.common.message.TransportMessage;
import com.landleaf.homeauto.mqtt.config.RsocketClientConfig;
import com.landleaf.homeauto.mqtt.transport.DirectHandler;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.mqtt.*;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.time.Duration;


@Slf4j
public class PubHandler implements DirectHandler {


    @Override
    public void handler(MqttMessage message, TransportConnection connection, RsocketConfiguration config) {
            RsocketClientConfig clientConfig = (RsocketClientConfig) config;
            MqttFixedHeader header = message.fixedHeader();
            switch (header.messageType()) {
                case PUBLISH:
                    MqttPublishMessage mqttPublishMessage = (MqttPublishMessage) message;
                    MqttPublishVariableHeader variableHeader = mqttPublishMessage.variableHeader();
                    ByteBuf byteBuf = mqttPublishMessage.payload();
                    byte[] bytes = copyByteBuf(byteBuf);
                    switch (header.qosLevel()) {
                        case AT_MOST_ONCE:
                            clientConfig.getMessageAcceptor().accept(variableHeader.topicName(), bytes);
                            break;
                        case AT_LEAST_ONCE:
                            clientConfig.getMessageAcceptor().accept(variableHeader.topicName(), bytes);
                            MqttPubAckMessage mqttPubAckMessage = MqttMessageApi.buildPuback(header.isDup(), header.qosLevel(), header.isRetain(), variableHeader.packetId()); // back
                            connection.write(mqttPubAckMessage).subscribe();
                            break;
                        case EXACTLY_ONCE:
                            int messageId = variableHeader.packetId();
                            MqttPubAckMessage mqttPubRecMessage = MqttMessageApi.buildPubRec(messageId);
                            connection.addDisposable(messageId, Mono.fromRunnable(() ->
                                    connection.write(MqttMessageApi.buildPubRec(messageId)).subscribe()) // retry send rec
                                    .delaySubscription(Duration.ofSeconds(10)).repeat().subscribe());
                            connection.write(mqttPubRecMessage).subscribe();  //  send rec
                            TransportMessage transportMessage = TransportMessage.builder().isRetain(header.isRetain())
                                    .isDup(false)
                                    .topic(variableHeader.topicName())
                                    .message(bytes)
                                    .qos(header.qosLevel().value())
                                    .build();
                            connection.saveQos2Message(messageId, transportMessage);
                            break;
                        case FAILURE:
                            log.error(" publish FAILURE {} {} ", header, variableHeader);
                            break;
                    }
                    break;
                case PUBACK:
                    MqttMessageIdVariableHeader Back = (MqttMessageIdVariableHeader) message.variableHeader();
                    connection.cancelDisposable(Back.messageId());
                    break;
                case PUBREC:
                    MqttMessageIdVariableHeader recVH = (MqttMessageIdVariableHeader) message.variableHeader();
                    int id = recVH.messageId();
                    connection.cancelDisposable(id);
                    connection.write(MqttMessageApi.buildPubRel(id)).subscribe();  //  send rel
                    connection.addDisposable(id, Mono.fromRunnable(() ->
                            connection.write(MqttMessageApi.buildPubRel(id)).subscribe())
                            .delaySubscription(Duration.ofSeconds(10)).repeat().subscribe()); // retry
                    break;
                case PUBREL:
                    MqttMessageIdVariableHeader b = (MqttMessageIdVariableHeader) message.variableHeader();
                    int messageId = b.messageId();
                    connection.cancelDisposable(messageId); // cancel replay rec
                    MqttPubAckMessage mqttPubRecMessage = MqttMessageApi.buildPubComp(messageId);
                    connection.write(mqttPubRecMessage).subscribe();  //  send comp
                    connection.getAndRemoveQos2Message(messageId)
                            .ifPresent(msg -> clientConfig.getMessageAcceptor().accept(msg.getTopic(), msg.getMessage()));
                    break;
                case PUBCOMP:
                    MqttMessageIdVariableHeader compVH = (MqttMessageIdVariableHeader) message.variableHeader();
                    connection.cancelDisposable(compVH.messageId());
                    break;
            }
    }

    private byte[] copyByteBuf(ByteBuf byteBuf) {
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        return bytes;
    }


}
