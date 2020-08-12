package com.landleaf.homeauto.contact.screen.service;

import com.landleaf.homeauto.contact.screen.dto.ContactScreenDomain;

import java.util.Observer;

/**
 * 超时的逻辑定义
 */
public interface MqttCloudToScreenTimeoutService extends Observer {
    /**
     * 添加需要超时的任务
     */
    void addTimeoutTask(ContactScreenDomain messageDomain);

    /**
     * 移除需超时任务
     */
    ContactScreenDomain rmTimeoutTask(String  messageKey);
}
