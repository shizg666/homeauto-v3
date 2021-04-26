package com.landleaf.homeauto.center.device.eventbus.event;

import com.landleaf.homeauto.center.device.service.mybatis.ITemplateOperateService;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.redis.RedisUtils;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executor;
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
@Slf4j
public class TemplateOperateEventHolder {
    @Autowired
    private ITemplateOperateService iTemplateOperateService;

    private DelayQueue<TemplateOperateEvent> queue = new DelayQueue<TemplateOperateEvent>();

    @Autowired
    private Executor bussnessExecutor;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 消息延时时间 5分钟
     */
    Long MESSAGE_EXPIRE = 5*60L;

    //0 未启动 1启动
    private volatile  int handStatus = 0;

    public void handleMessage(){
        bussnessExecutor.execute(new Runnable() {
            @Override
            public void run() {
                TemplateOperateEvent event = null;
                while (null != (event = queue.poll())){
                    log.info("******************************************发送户型变更消息:{}",event.getTemplateId());
                    iTemplateOperateService.notifyTemplateUpdate(event);
                }
                handStatus = 0;
            }
        });

    }

    public boolean ishanding() {
        return handStatus==0?false:true;
    }

    public void addEvent(TemplateOperateEvent event) {
        if (!redisUtils.getLock(RedisCacheConst.TEMPLATE_OPERATE_MESSAGE.concat(String.valueOf(event.getTemplateId())),
                MESSAGE_EXPIRE)){
            return;
        }
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
        handleMessage();
    }
}
