package com.landleaf.homeauto.mqtt.protocol;

import com.landleaf.homeauto.mqtt.common.annocation.ProtocolType;
import com.landleaf.homeauto.mqtt.protocol.mqtt.MqttProtocol;
import com.landleaf.homeauto.mqtt.protocol.ws.WsProtocol;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProtocolFactory {

    private  List<Protocol> protocols = new ArrayList<>();

    public ProtocolFactory(){
        protocols.add(new MqttProtocol());
        protocols.add(new WsProtocol());
    }


    public void  registryProtocl(Protocol protocol){
        protocols.add(protocol);
    }

    public Optional<Protocol>  getProtocol(ProtocolType protocolType){
        return protocols.stream().filter(protocol -> protocol.support(protocolType)).findAny();
    }



}
