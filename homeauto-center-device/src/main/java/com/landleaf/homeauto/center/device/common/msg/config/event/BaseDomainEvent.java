package com.landleaf.homeauto.center.device.common.msg.config.event;


import java.util.Date;

/**
 * 事件抽象类
 */
public abstract class BaseDomainEvent {

    private Date occurredTime;

    protected abstract String identify();


    public BaseDomainEvent() {
        occurredTime = new Date();
    }

    public BaseDomainEvent(Date time) {
        occurredTime = time;
    }

    public Date occurredTime(){
        return occurredTime;
    }
}
