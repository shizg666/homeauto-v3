package com.landleaf.homeauto.common.rocketmq.producer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "homeauto.rocketmq.producer")
public class MQProducerConfig {

    /**
     * 发送同一类消息的设置为同一个group，保证唯一,默认不需要设置，rocketmq会使用ip@pid(pid代表jvm名字)作为唯一标示
     */
    private String groupName;
    private String namesrvAddr;
    private String instanceName;
    /**
     * 消息最大大小，默认4M
     */
    private Integer maxMessageSize;
    /**
     * 消息发送超时时间，默认3秒
     */
    private Integer sendMsgTimeout;
    /**
     * 消息发送失败重试次数，默认2次
     */
    private Integer retryTimesWhenSendFailed;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public Integer getMaxMessageSize() {
        return maxMessageSize;
    }

    public void setMaxMessageSize(Integer maxMessageSize) {
        this.maxMessageSize = maxMessageSize;
    }

    public Integer getSendMsgTimeout() {
        return sendMsgTimeout;
    }

    public void setSendMsgTimeout(Integer sendMsgTimeout) {
        this.sendMsgTimeout = sendMsgTimeout;
    }

    public Integer getRetryTimesWhenSendFailed() {
        return retryTimesWhenSendFailed;
    }

    public void setRetryTimesWhenSendFailed(Integer retryTimesWhenSendFailed) {
        this.retryTimesWhenSendFailed = retryTimesWhenSendFailed;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }
}
