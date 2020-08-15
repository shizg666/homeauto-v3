package com.landleaf.homeauto.center.websocket.service.rocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * @author Yujiumin
 * @version 2020/8/14
 */
public class ProduceService {

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("DEFAULT_GROUP");
        producer.setInstanceName("Yujiumin");
        producer.setRetryTimesWhenSendFailed(3);
        producer.setNamesrvAddr("52.130.74.157:9876");
        producer.setVipChannelEnabled(false);
        producer.start();

        MessageExt messageExt = new MessageExt();
        messageExt.setBody("Hello".getBytes());
        messageExt.setTopic("TEST_TOPIC");
        messageExt.setTags(null);
        producer.send(messageExt, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println(sendResult.getMessageQueue().getTopic());
            }

            @Override
            public void onException(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

}
