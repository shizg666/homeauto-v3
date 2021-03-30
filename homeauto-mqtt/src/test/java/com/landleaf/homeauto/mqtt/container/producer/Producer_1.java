package com.landleaf.homeauto.mqtt.container.producer;

import com.landleaf.homeauto.mqtt.api.client.RsocketClientSession;
import com.landleaf.homeauto.mqtt.common.annocation.ProtocolType;
import com.landleaf.homeauto.mqtt.transport.client.TransportClient;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;


public class Producer_1 {

    @Test
    public void testClient() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
    RsocketClientSession clientSession= TransportClient.create("42.159.210.101",1883)
              .heart(10000)
              .protocol(ProtocolType.MQTT)
              .ssl(false)
              .log(true)
              .clientId("1111121212")
            .username("admin")
            .password("public")
            .onClose(()->{})
            .willMessage("123")
            .willTopic("/lose")
              .exception(throwable -> System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&"+throwable))
              .messageAcceptor((topic,msg)->{
                    System.out.println(topic+":"+new String(msg));
               })
              .connect()
              .block();
        latch.await();



    }

}
