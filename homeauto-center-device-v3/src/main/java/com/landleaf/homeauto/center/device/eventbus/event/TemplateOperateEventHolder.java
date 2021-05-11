package com.landleaf.homeauto.center.device.eventbus.event;

import com.landleaf.homeauto.center.device.service.mybatis.ITemplateOperateService;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.redis.RedisUtils;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Objects;
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
    private Executor templateOperateExecutor;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 消息延时时间 5分钟
     */
    Long MESSAGE_EXPIRE = 3*60L;
    /**
     * 延时时间 毫秒 5分钟
     */
    Long MESSAGE_TIME = 3*60*1000L;

    //0 未启动 1启动
    private volatile  int handStatus = 0;

    public void handleMessage(){
        this.handStatus = 1;
        templateOperateExecutor.execute(new Runnable() {
            @Override
            public void run() {
                TemplateOperateEvent event = null;
                for(;;){
                    try {
                        event = queue.take();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(Objects.nonNull(event)){
                        log.info("******************************************发送户型变更消息:{}",event.getTemplateId());
                        iTemplateOperateService.notifyTemplateUpdate(event);
                    }
                    //若队列为空则停止
                    if (queue.isEmpty()){
                        log.info("******************************************队列数据完成:{}");
                        break;
                    }
                }
                handStatus = 0;
                log.info("******************************************结束:{}");
            }
        });

    }

    public boolean ishanding() {
        return handStatus==0?false:true;
    }

    public void addEvent(TemplateOperateEvent event) {
        String lock = String.format(RedisCacheConst.TEMPLATE_OPERATE_MESSAGE, String.valueOf(event.getTemplateId()),event.getTypeEnum().code);
        if (!redisUtils.getLock(lock, MESSAGE_EXPIRE)){
            return;
        }
        event.setSendTime(System.currentTimeMillis() + MESSAGE_TIME);
        queue.add(event);
        if (handStatus == 0){
            setHandStatus();
        }
    }

    public synchronized void setHandStatus() {
        if (handStatus == 1){
            return;
        }
        handleMessage();
    }
}
