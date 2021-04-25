package com.landleaf.homeauto.center.device.eventbus.subscriber;

import com.google.common.eventbus.Subscribe;
import com.landleaf.homeauto.center.device.eventbus.event.DeviceOperateEvent;
import com.landleaf.homeauto.center.device.eventbus.event.TemplateOperateEvent;
import com.landleaf.homeauto.center.device.eventbus.event.TemplateOperateEventHolder;
import com.landleaf.homeauto.center.device.eventbus.publisher.TemplateOperateEventPublisher;
import com.landleaf.homeauto.center.device.eventbus.publisher.base.GuavaDomainEventPublisher;
import com.landleaf.homeauto.center.device.service.mybatis.ITemplateOperateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 */
@Component
@Slf4j
public class TemplateOperateEventSubscriber extends GuavaDomainEventPublisher {

    private TemplateOperateEventHolder templateOperateEventHolder;
    private TemplateOperateEventPublisher templateOperateEventPublisher;


    @Autowired
    public TemplateOperateEventSubscriber(TemplateOperateEventHolder templateOperateEventHolder, TemplateOperateEventPublisher templateOperateEventPublisher) {
        this.templateOperateEventHolder = templateOperateEventHolder;
        this.templateOperateEventPublisher = templateOperateEventPublisher;
        templateOperateEventPublisher.register(this);
    }

    @Subscribe
    public void handleEvent(TemplateOperateEvent event) {
        try{
            if (templateOperateEventHolder.ishanding()){
                return;
            }
            templateOperateEventHolder.setHandStatus(1);
            templateOperateEventHolder.handleMessage();
        }catch (Exception e){
            log.error("energyDataShowEvent -------------->报错:{}",e.getMessage());
        }

    }
}
