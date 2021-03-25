package com.landleaf.homeauto.center.device.eventbus.event;

import com.landleaf.homeauto.center.device.eventbus.event.base.BaseDomainEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName DeviceOperateEvent
 * @Description: TODO
 * @Author shizg
 * @Date 2021/1/21
 * @Version V1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceOperateEvent extends BaseDomainEvent {
    private String familyCode;

    private String templateId;

    private String deviceCode;

    private String deviceId;

    private int type;

    private int retryCount;

    //故障属性code
    List<String> errorAttrCodes;

    public DeviceOperateEvent(int retryCount) {
        this.retryCount = retryCount;
    }

    @Override
    protected String identify() {
        return null;
    }
}
