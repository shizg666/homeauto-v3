package com.landleaf.homeauto.mqtt.protocol;


import com.landleaf.homeauto.mqtt.common.annocation.ProtocolType;
import io.netty.channel.ChannelHandler;

import java.util.List;

public interface Protocol {


    boolean support(ProtocolType protocolType);

    ProtocolTransport getTransport();

    List<ChannelHandler> getHandlers();

}
