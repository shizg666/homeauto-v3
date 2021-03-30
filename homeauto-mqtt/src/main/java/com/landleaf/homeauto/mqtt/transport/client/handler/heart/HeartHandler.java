package com.landleaf.homeauto.mqtt.transport.client.handler.heart;

import com.landleaf.homeauto.mqtt.api.RsocketConfiguration;
import com.landleaf.homeauto.mqtt.api.TransportConnection;
import com.landleaf.homeauto.mqtt.transport.DirectHandler;
import io.netty.handler.codec.mqtt.MqttMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeartHandler implements DirectHandler {


    @Override
    public void handler(MqttMessage message, TransportConnection connection, RsocketConfiguration config) {
            switch (message.fixedHeader().messageType()){
                case PINGRESP:
                break;
            }

    }
}
