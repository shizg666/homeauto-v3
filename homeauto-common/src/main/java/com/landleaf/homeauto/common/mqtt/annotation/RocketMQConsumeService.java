package com.landleaf.homeauto.common.mqtt.annotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;


/**
 * 此注解用于标注RocketMQ消费者服务
 * @author wenyilu
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Service
public @interface RocketMQConsumeService {
    /**
     * 消息主题
     */
     String topic();

    /**
     * 消息标签,如果是该主题下所有的标签，使用“*”
     */
     String[] tags();


}
