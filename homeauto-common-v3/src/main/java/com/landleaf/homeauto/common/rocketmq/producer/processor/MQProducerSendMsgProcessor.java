package com.landleaf.homeauto.common.rocketmq.producer.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.dto.BaseRocketMQMessageDTO;
import com.landleaf.homeauto.common.exception.RocketMQException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 生产者发送消息处理器
 * .<br/>
 */
@ConditionalOnProperty(prefix = "homeauto.rocketmq.producer", name = "enable")
@Component
public class MQProducerSendMsgProcessor {
    private static final Logger logger = LoggerFactory.getLogger(MQProducerSendMsgProcessor.class);
    @Autowired
    private DefaultMQProducer defaultMQProducer;

    /**
     * 发送消息,仅发送一次，不关心是否发送成功
     *
     * @param topic 主题
     * @param tag   消息标签，只支持设置一个Tag（服务端消息过滤使用）
     * @param keys  消息关键词，多个Key用MessageConst.KEY_SEPARATOR隔开（查询消息使用）
     * @param msg   消息
     *              2018年2月28日 zhaowg
     */
    public void sendOneway(String topic, String tag, String keys, BaseRocketMQMessageDTO msg) {
        this.sendOneway(topic, tag, keys, JSON.toJSONString(msg));
    }

    /**
     * 发送消息,仅发送一次，不关心是否发送成功
     *
     * @param topic 主题
     * @param tag   消息标签，只支持设置一个Tag（服务端消息过滤使用）
     * @param keys  消息关键词，多个Key用MessageConst.KEY_SEPARATOR隔开（查询消息使用）
     * @param msg   消息
     */
    public void sendOneway(String topic, String tag, String keys, String msg) {
//        logger.info(String.format("发送信息到消息队列(只发送一次，不关心是否成功)。topic:%s,tag:%s,keys:%s,xml:%s", topic == null ? "" : topic + "[" + topic + "]",
//                tag == null ? "" : tag, keys.toString(), msg));
        try {
            validateSendMsg(topic, tag, msg);
            Message sendMsg = new Message(topic, tag == null ? null : tag, StringUtils.isEmpty(keys) ? null : keys, msg.getBytes());
            //默认3秒超时
            defaultMQProducer.sendOneway(sendMsg);
        } catch (MQClientException | RemotingException | InterruptedException e) {
            logger.error("消息发送失败", e);
        }
    }

    /**
     * 同步发送消息
     *
     * @param topic 主题
     * @param tag   消息标签，只支持设置一个Tag（服务端消息过滤使用）
     * @param msg   消息
     * @return
     */
    public MQSendResult send(String topic, String tag, String msg) {
        return send(topic, tag, null, msg);
    }

    /**
     * 同步发送消息
     *
     * @param topic 主题
     * @param tag   消息标签，只支持设置一个Tag（服务端消息过滤使用）
     * @param keys  消息关键词，多个Key用MessageConst.KEY_SEPARATOR隔开（查询消息使用）
     * @param msg   消息
     * @return
     */
    public MQSendResult send(String topic, String tag, String keys, String msg) {
//        logger.info(String.format("发送信息到消息队列。topic:%s,tag:%s,keys:%s,xml:%s", topic == null ? "" : topic + "[" + topic + "]",
//                tag == null ? "" : tag, keys, msg));
        MQSendResult mqSendResult = null;
        try {
            validateSendMsg(topic, tag, msg);
            SendResult sendResult = null;
            Message sendMsg = new Message(topic, tag == null ? null : tag, StringUtils.isEmpty(keys) ? null : keys, msg.getBytes());
            //默认3秒超时
            sendResult = defaultMQProducer.send(sendMsg);
            mqSendResult = new MQSendResult(sendResult);
        } catch (RocketMQException e) {
            logger.error(e.getMessage());
            mqSendResult = new MQSendResult(e.getMessage(), null);
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            logger.error("消息发送失败", e);
            mqSendResult = new MQSendResult("消息发送失败", e);
        }
//        logger.info("发送消息到消息队列的响应信息为：" + mqSendResult.toString());
        return mqSendResult == null ? new MQSendResult() : mqSendResult;
    }

    /**
     * 校验参数
     *
     * @param topic
     * @param tag
     * @param msg
     */
    private void validateSendMsg(String topic, String tag, String msg) {
        if (StringUtils.isEmpty(topic)) {
            throw new RocketMQException(ErrorCodeEnumConst.ROCKETMQ_TOPIC_EMPTY);
        }
        if (tag == null) {
            throw new RocketMQException(ErrorCodeEnumConst.ROCKETMQ_TAG_EMPTY);
        }
        if (msg == null) {
            throw new RocketMQException(ErrorCodeEnumConst.ROCKETMQ_MSG_EMPTY);
        }
    }

}
