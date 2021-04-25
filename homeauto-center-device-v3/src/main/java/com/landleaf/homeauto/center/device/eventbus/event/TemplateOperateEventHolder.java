package com.landleaf.homeauto.center.device.eventbus.event;

import com.landleaf.homeauto.center.device.service.mybatis.ITemplateOperateService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.DelayQueue;

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
    }

    public boolean ishanding() {
        return handStatus==0?false:true;
    }
}
