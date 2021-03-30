package com.landleaf.homeauto.mqtt.container.consumer;

import com.landleaf.homeauto.mqtt.api.client.RsocketClientSession;
import com.landleaf.homeauto.mqtt.common.annocation.ProtocolType;
import com.landleaf.homeauto.mqtt.transport.client.TransportClient;
import io.netty.handler.codec.mqtt.MqttQoS;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;


public class Comsumer_2 {

    @Test
    public void testClient() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
    RsocketClientSession clientSession= TransportClient.create("192.168.100.225",1883)
              .heart(10000)
              .protocol(ProtocolType.MQTT)
              .ssl(false)
              .log(true)
              .clientId("Comsumer_2")
                .password("1234abcd")
            .username("admin")
            .willMessage("Comsumer_2")
            .willTopic("/lose/Comsumer_2")
            .willQos(MqttQoS.AT_LEAST_ONCE)
              .exception(throwable -> System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&"+throwable))
              .messageAcceptor((topic,msg)-> System.out.println(topic+":"+new String(msg)))
              .connect()
              .block();
//        clientSession.sub("test").subscribe();
        latch.await();



    }

}
