package com.landleaf.homeauto.center.device.service.mybatis;

import com.landleaf.homeauto.center.device.eventbus.event.DeviceOperateEvent;

/**
 * <p>
 * 户型设备表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
public interface IDeviceMessageService {

    void sendDeviceOperaMessage(DeviceOperateEvent deviceOperateEvent);

}
