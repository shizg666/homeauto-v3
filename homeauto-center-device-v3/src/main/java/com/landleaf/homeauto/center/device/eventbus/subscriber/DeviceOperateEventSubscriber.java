package com.landleaf.homeauto.center.device.eventbus.subscriber;

import com.google.common.eventbus.Subscribe;
import com.landleaf.homeauto.center.device.eventbus.publisher.DeviceOperateEventPublisher;
import com.landleaf.homeauto.center.device.eventbus.publisher.base.GuavaDomainEventPublisher;
import com.landleaf.homeauto.center.device.eventbus.event.DeviceOperateEvent;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 */
@Component
@Slf4j
public class DeviceOperateEventSubscriber extends GuavaDomainEventPublisher {
    private IHouseTemplateDeviceService iHouseTemplateDeviceService;
    private DeviceOperateEventPublisher deviceOperateEventPublisher;

    @Autowired
    public DeviceOperateEventSubscriber(IHouseTemplateDeviceService iHouseTemplateDeviceService, DeviceOperateEventPublisher deviceOperateEventPublisher) {
        this.iHouseTemplateDeviceService = iHouseTemplateDeviceService;
        this.deviceOperateEventPublisher = deviceOperateEventPublisher;
        deviceOperateEventPublisher.register(this);
    }

    @Subscribe
    public void handleDeviceAttrEvent(DeviceOperateEvent event) {

        if (event.getRetryCount() >2){
            log.error("energyDataShowEvent -------------->发了2次依然报错！！！！！！！");
            return;
        }
        event.setRetryCount(event.getRetryCount()+1);
        try{
            iHouseTemplateDeviceService.errorAttrInfoCache(event);
        }catch (Exception e){
            log.error("energyDataShowEvent -------------->报错:{}",e.getMessage());
            deviceOperateEventPublisher.asyncPublish(event);
        }

    }
}
