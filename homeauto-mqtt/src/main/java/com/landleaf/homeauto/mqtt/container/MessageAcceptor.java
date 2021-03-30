package com.landleaf.homeauto.mqtt.container;

public interface MessageAcceptor {

    void accept(String topic,byte[] message);

}
