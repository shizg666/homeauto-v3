package com.landleaf.homeauto.center.device.eventbus.publisher;

import com.landleaf.homeauto.center.device.eventbus.event.TemplateOperateEvent;
import com.landleaf.homeauto.center.device.eventbus.event.TemplateOperateEventHolder;
import com.landleaf.homeauto.center.device.eventbus.event.base.BaseDomainEvent;
import com.landleaf.homeauto.center.device.eventbus.publisher.base.GuavaDomainEventPublisher;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 户型相关操作
 */
@Component
@Slf4j
public class TemplateOperateEventPublisher extends GuavaDomainEventPublisher {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private TemplateOperateEventHolder templateOperateEventHolder;
    /**
     * 消息过期时间 10分钟
     */
    Long MESSAGE_EXPIRE = 10*60L;


    public void publish(TemplateOperateEvent event) {
        if (event.getRetryCount() >2){
            log.error("energyDataShowEvent -------------->发了2次依然报错！！！！！！！");
            return;
        }
        if (redisUtils.getLock(RedisCacheConst.TEMPLATE_OPERATE_MESSAGE.concat(String.valueOf(event.getTemplateId())),
                MESSAGE_EXPIRE)){
            return;
        }
        templateOperateEventHolder.setHandStatus(templateOperateEventHolder.getHandStatus());
        templateOperateEventHolder.addEvent(event);
        super.publish(event);

    }

}
