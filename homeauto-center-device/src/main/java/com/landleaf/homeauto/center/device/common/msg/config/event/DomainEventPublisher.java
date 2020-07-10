package com.landleaf.homeauto.center.device.common.msg.config.event;

public interface DomainEventPublisher<T extends BaseDomainEvent> {


    /**
     * 注册监听事件
     * @param listener
     */
    void register(Object listener);

    /**
     * 同步推送事件
     * @param event
     */
    void publish(T event);

    /**
     * 异步推送事件
     * @param event
     */
    void asyncPublish(T event);
}
