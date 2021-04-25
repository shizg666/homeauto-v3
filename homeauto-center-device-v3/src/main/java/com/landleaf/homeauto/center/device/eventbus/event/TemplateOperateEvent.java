package com.landleaf.homeauto.center.device.eventbus.event;

import com.landleaf.homeauto.center.device.eventbus.event.base.BaseDomainEvent;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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
public class TemplateOperateEvent extends BaseDomainEvent implements Delayed {

    private Long templateId;

    /**
     * 发送时间
     */
    private long creaTime;

    private int retryCount = 1;

    private ContactScreenConfigUpdateTypeEnum typeEnum;

    public TemplateOperateEvent(int retryCount) {
        this.retryCount = retryCount;
    }

    @Override
    protected String identify() {
        return null;
    }


    @Override
    public int compareTo(Delayed o) {
        return this.getCreaTime() > ((TemplateOperateEvent) o).getCreaTime() ? 1 : -1;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(creaTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }
}
