package com.landleaf.homeauto.mqtt.transport.client.handler.sub;

import com.landleaf.homeauto.mqtt.api.RsocketConfiguration;
import com.landleaf.homeauto.mqtt.api.TransportConnection;
import com.landleaf.homeauto.mqtt.transport.DirectHandler;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageIdVariableHeader;


public class SubHandler implements DirectHandler {


    @Override
    public void handler(MqttMessage message, TransportConnection connection, RsocketConfiguration config) {
            MqttFixedHeader header=  message.fixedHeader();
            MqttMessageIdVariableHeader mqttMessageIdVariableHeader =(MqttMessageIdVariableHeader) message.variableHeader();
            switch (header.messageType()){
                case  SUBACK:
                case UNSUBACK:
                    connection.cancelDisposable(mqttMessageIdVariableHeader.messageId());
                    break;
            }
    }
}
