package com.landleaf.homeauto.contact.screen.service;

import com.landleaf.homeauto.contact.screen.dto.ContactScreenDomain;

import java.util.concurrent.DelayQueue;

/**
 * TimeoutService的监听，用于执行change事件
 */
public interface MqttCloudToScreenTimeoutObserverService {
    /**
     * 移动task的位置
     */
    void mvTask(DelayQueue<ContactScreenDomain> queue);
}
