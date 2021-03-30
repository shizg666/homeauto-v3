package com.landleaf.homeauto.mqtt.protocol.mqtt;

import com.google.common.collect.Lists;
import com.landleaf.homeauto.mqtt.common.annocation.ProtocolType;
import com.landleaf.homeauto.mqtt.protocol.Protocol;
import com.landleaf.homeauto.mqtt.protocol.ProtocolTransport;
import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;

import java.util.List;


public class MqttProtocol implements Protocol {


    @Override
    public boolean support(ProtocolType protocolType) {
        return protocolType == ProtocolType.MQTT;
    }

    @Override
    public ProtocolTransport getTransport() {
        return  new MqttTransport(this);
    }

    @Override
    public List<ChannelHandler> getHandlers() {
        return Lists.newArrayList( new MqttDecoder(5*1024*1024),MqttEncoder.INSTANCE);
    }
}
