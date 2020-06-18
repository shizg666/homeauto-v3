package com.landleaf.homeauto.contact.gateway.rocketmq;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendCallback;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.landleaf.homeauto.common.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * rocketmq生产者
 */
@Component
public class WebMqProducer {

    public static final Logger LOGGER = LoggerFactory.getLogger(WebMqProducer.class);

    @Autowired
    private DefaultMQProducer rocketmqProducer;


    //发送数据
    public void sendMessage(String sendMsg, String topic, String tag) {
        String key = RandomUtil.generateNumberString(10);
        Message msg = new Message(topic, tag, key, sendMsg.getBytes());
        try {
            rocketmqProducer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    LOGGER.info("发送MQ消息成功【topic】:{};【tag】:{};【消息】:{}", topic, tag, msg.toString());
                    sendResult.getSendStatus();
                }

                @Override
                public void onException(Throwable e) {
                    LOGGER.error("发送MQ消息失败:【topic】:{};【tag】:{};消息{};异常:{}", topic, tag, msg.toString(), e.getMessage(), e);
                }
            });
        } catch (MQClientException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (RemotingException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
