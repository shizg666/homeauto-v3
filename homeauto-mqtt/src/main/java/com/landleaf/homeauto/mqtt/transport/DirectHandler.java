package com.landleaf.homeauto.mqtt.transport;

import com.landleaf.homeauto.mqtt.api.RsocketConfiguration;
import com.landleaf.homeauto.mqtt.api.TransportConnection;
import io.netty.handler.codec.mqtt.MqttMessage;

public interface DirectHandler {

    void handler(MqttMessage message, TransportConnection connection, RsocketConfiguration config);

}
