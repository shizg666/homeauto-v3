package com.landleaf.homeauto.center.device.eventbus.event;

import com.landleaf.homeauto.center.device.service.mybatis.IFamilyOperateService;
import com.landleaf.homeauto.center.device.service.mybatis.ITemplateOperateService;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.redis.RedisUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.*;

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
public class FamilyOperateEventHolder {
    @Autowired
    private IFamilyOperateService iFamilyOperateService;

    private LinkedBlockingQueue<FamilyOperateEvent> queue = new LinkedBlockingQueue<FamilyOperateEvent>();

    @Autowired
    private Executor familyOperateExecutor;

    @Autowired
    private RedisUtils redisUtils;


    public void handleMessage(){
        familyOperateExecutor.execute(new Runnable() {
            @Override
            public void run() {
                FamilyOperateEvent event = null;
                for(;;){
                    try {
                        event = queue.take();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(Objects.nonNull(event)){
                        log.info("******************************************发送家庭变更消息:{}",event.getFamilyId());
                        iFamilyOperateService.notifyTemplateUpdate(event);
                    }
                    //若队列为空则停止
                    if (queue.isEmpty()){
                        log.info("******************************************家庭队列数据完成:{}");
                        break;
                    }
                }
            }
        });

    }

    public void addEvent(FamilyOperateEvent event) {
        queue.add(event);
        handleMessage();
    }


}
