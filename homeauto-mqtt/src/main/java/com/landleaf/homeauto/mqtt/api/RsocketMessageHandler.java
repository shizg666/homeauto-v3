package com.landleaf.homeauto.mqtt.api;


import com.landleaf.homeauto.mqtt.common.connection.RetainMessage;

import java.util.Optional;

/**
 * manage message
 */
public interface RsocketMessageHandler {

    void saveRetain(boolean dup, boolean retain, int qos, String topicName, byte[] copyByteBuf);

    Optional<RetainMessage> getRetain(String topicName);
}
