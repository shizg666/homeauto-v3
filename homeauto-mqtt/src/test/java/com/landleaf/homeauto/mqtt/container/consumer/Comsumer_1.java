package com.landleaf.homeauto.mqtt.container.consumer;

import com.landleaf.homeauto.mqtt.api.client.RsocketClientSession;
import com.landleaf.homeauto.mqtt.common.annocation.ProtocolType;
import com.landleaf.homeauto.mqtt.transport.client.TransportClient;
import io.netty.handler.codec.mqtt.MqttQoS;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;


public class Comsumer_1 {

    @Test
    public void testClient() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
    RsocketClientSession clientSession= TransportClient.create("42.159.210.101",1883)
              .heart(10000)
              .protocol(ProtocolType.MQTT)
              .ssl(false)
              .log(false)
              .clientId("Comsumer_1")
                .password("12")
            .username("123")
            .willMessage("Comsumer_1")
            .willTopic("/lose/Comsumer_1")
            .willQos(MqttQoS.AT_LEAST_ONCE)
              .exception(throwable -> System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&"+throwable))
              .messageAcceptor((topic,msg)->{
                  try {
                      // 可以再扩展，定义处理特定topic的handle
                      System.out.println(topic+":"+ new String(msg));
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
              })
              .connect()
              .block();
        Thread.sleep(5000);
        clientSession.sub("/dict/asr_resualt/test1234").subscribe();
        latch.await();



    }

}
