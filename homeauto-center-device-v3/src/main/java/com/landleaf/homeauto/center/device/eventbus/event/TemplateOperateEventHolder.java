package com.landleaf.homeauto.center.device.eventbus.event;

import com.landleaf.homeauto.center.device.service.mybatis.ITemplateOperateService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

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
@Component
public class TemplateOperateEventHolder {
    @Autowired
    private ITemplateOperateService iTemplateOperateService;

    private DelayQueue<TemplateOperateEvent> queue = new DelayQueue<TemplateOperateEvent>();

    //0 未启动 1启动
    private volatile  int handStatus = 0;

    public void handleMessage(){
        TemplateOperateEvent event = null;
        while (null != (event = queue.poll())){
            iTemplateOperateService.notifyTemplateUpdate(event);
        }
        handStatus = 0;
    }

    public boolean ishanding() {
        return handStatus==0?false:true;
    }

    public void addEvent(TemplateOperateEvent event) {
        handStatus = 1;
        queue.add(event);
        if (handStatus == 0){
            setHandStatus();
        }
    }

    public synchronized void setHandStatus() {
        if (handStatus == 1){
            return;
        }
        this.handStatus = 1;
    }
}
