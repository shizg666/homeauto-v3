package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.landleaf.homeauto.center.device.eventbus.event.DeviceOperateEvent;
import com.landleaf.homeauto.center.device.eventbus.publisher.DeviceOperateEventPublisher;
import com.landleaf.homeauto.center.device.service.mybatis.IDeviceMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName IDeviceMessageServiceImpl
 * @Description: TODO
 * @Author shizg
 * @Date 2021/3/9
 * @Version V1.0
 **/
@Service
public class IDeviceMessageServiceImpl implements IDeviceMessageService {
    @Autowired
    private DeviceOperateEventPublisher deviceOperateEventPublisher;

    @Override
    public void sendDeviceOperaMessage(DeviceOperateEvent deviceOperateEvent) {
        deviceOperateEventPublisher.asyncPublish(deviceOperateEvent);
    }
}
