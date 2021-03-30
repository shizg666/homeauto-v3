package com.landleaf.homeauto.mqtt.transport.server.handler;


import io.netty.handler.codec.mqtt.MqttMessageType;

public interface DirectHandlerAdaptor {

    DirectHandlerFactory handler(MqttMessageType messageType);

}
