package com.landleaf.homeauto.center.device.common.msg.config.event;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * guava时间推送类
 *
 * @author wenyilu
 */
@Component
public class GuavaDomainEventPublisher implements DomainEventPublisher {

    private EventBus eventBus;

    private AsyncEventBus asyncEventBus;

    @Override
    public void register(Object listener) {
        eventBus.register(listener);
        asyncEventBus.register(listener);
    }

    @Override
    public void publish(BaseDomainEvent event) {
        eventBus.post(event);
    }

    @Override
    public void asyncPublish(BaseDomainEvent event) {
        asyncEventBus.post(event);
    }

    @Autowired
    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Autowired
    public void setAsyncEventBus(AsyncEventBus asyncEventBus) {
        this.asyncEventBus = asyncEventBus;
    }
}
