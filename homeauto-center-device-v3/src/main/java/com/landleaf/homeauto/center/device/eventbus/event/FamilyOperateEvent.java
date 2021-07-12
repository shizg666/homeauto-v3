package com.landleaf.homeauto.center.device.eventbus.event;

import com.landleaf.homeauto.center.device.eventbus.event.base.BaseDomainEvent;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

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
public class FamilyOperateEvent extends BaseDomainEvent implements Delayed {

//    private Long templateId;

    private Long familyId;

    /**
     * 发送时间
     */
    private long sendTime;

    private int retryCount = 1;

    private ContactScreenConfigUpdateTypeEnum typeEnum;

    @Override
    protected String identify() {
        return null;
    }


    @Override
    public int compareTo(Delayed o) {
        return this.getSendTime() > ((FamilyOperateEvent) o).getSendTime() ? 1 : -1;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(sendTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }
}
