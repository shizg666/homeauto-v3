package com.landleaf.homeauto.common.rocketmq.producer;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.exception.RocketMQException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * 生产者配置
 * .<br/>
 */
@Configuration
@ConditionalOnProperty(prefix = "homeauto.rocketmq.producer", name = "enable")
public class MQProducerConfiguration {
    public static final Logger LOGGER = LoggerFactory.getLogger(MQProducerConfiguration.class);

    @Autowired(required = false)
    private MQProducerConfig mqProducerConfig;

    @Bean
    public DefaultMQProducer defaultMQProducer() throws RocketMQException {
        String groupName = mqProducerConfig.getGroupName();
        String namesrvAddr = mqProducerConfig.getNamesrvAddr();
        Integer maxMessageSize = mqProducerConfig.getMaxMessageSize();
        Integer retryTimesWhenSendFailed = mqProducerConfig.getRetryTimesWhenSendFailed();
        Integer sendMsgTimeout = mqProducerConfig.getSendMsgTimeout();
        if (StringUtils.isEmpty(groupName)) {
            throw new RocketMQException(ErrorCodeEnumConst.ROCKETMQ_GROUPNAME_EMPTY);
        }
        if (StringUtils.isEmpty(namesrvAddr)) {
            throw new RocketMQException(ErrorCodeEnumConst.ROCKETMQ_NAMESERVERADDR_EMPTY);
        }
        DefaultMQProducer producer;
        producer = new DefaultMQProducer(groupName);
        producer.setNamesrvAddr(namesrvAddr);
        //如果需要同一个jvm中不同的producer往不同的mq集群发送消息，需要设置不同的instanceName
        //producer.setInstanceName(instanceName);
        if (maxMessageSize != null) {
            producer.setMaxMessageSize(maxMessageSize);
        }
        if (sendMsgTimeout != null) {
            producer.setSendMsgTimeout(sendMsgTimeout);
        }
        //如果发送消息失败，设置重试次数，默认为2次
        if (retryTimesWhenSendFailed != null) {
            producer.setRetryTimesWhenSendFailed(retryTimesWhenSendFailed);
        }
        if(!StringUtils.isEmpty(mqProducerConfig.getInstanceName())){
            producer.setInstanceName(mqProducerConfig.getInstanceName());
        }
        try {
            producer.start();

            LOGGER.info(String.format("producer is start ! groupName:[%s],namesrvAddr:[%s]"
                    , groupName, namesrvAddr));
        } catch (MQClientException e) {
            LOGGER.error(String.format("producer is error {}"
                    , e.getMessage(), e));
            throw new BusinessException();
        }
        return producer;
    }


}