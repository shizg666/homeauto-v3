package com.landleaf.homeauto.contact.screen.service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.request.*;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenNameEnum;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenDomain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Observable;
import java.util.concurrent.DelayQueue;

/**
 * 超时的逻辑实现
 *
 * @author wenyilu
 */
@Service
@Slf4j
public class MqttCloudToScreenTimeoutServiceImpl extends Observable implements MqttCloudToScreenTimeoutService {

    @PostConstruct
    protected void addObserver() {
        this.addObserver(this);
    }

    private DelayQueue<ContactScreenDomain> queue = new DelayQueue<ContactScreenDomain>();

    @Autowired
    private MqttCloudToScreenTimeoutObserverService mqttCloudToScreenTimeoutObserverService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTimeoutTask(ContactScreenDomain messageDomain) {
        String dto_key = RedisCacheConst.CONTACT_SCREEN_MSG_DTO_CACHE_PREFIX.concat(messageDomain.getMessageKey());
        redisUtils.set(dto_key, messageDomain, RedisCacheConst.CONTACT_SCREEN_MESSAGE_COMMON_EXPIRE);
        queue.offer(messageDomain);
        setChanged();
        notifyObservers();
    }

    /**
     * {@inheritDoc} 移除超时任务
     */
    @Override
    public ContactScreenDomain rmTimeoutTask(String messageKey, String operateName) {
        ContactScreenDomain orginDomain = null;
        log.info("收到ack返回，redis的messagekey为{}", RedisCacheConst.CONTACT_SCREEN_MSG_DTO_CACHE_PREFIX.concat(messageKey));
        if (!redisUtils.hasKey(RedisCacheConst.CONTACT_SCREEN_MSG_DTO_CACHE_PREFIX.concat(messageKey))) {
            return null;
        }
        redisUtils.set(RedisCacheConst.CONTACT_SCREEN_MSG_DTO_ACK_PREFIX.concat(messageKey), messageKey, RedisCacheConst.COMMON_EXPIRE);

        Object o = redisUtils.get(RedisCacheConst.CONTACT_SCREEN_MSG_DTO_CACHE_PREFIX.concat(messageKey));

        // 删除原始信息
        redisUtils.del(RedisCacheConst.CONTACT_SCREEN_MSG_DTO_CACHE_PREFIX.concat(messageKey));
        if (o != null) {
            if (StringUtils.equals(operateName, ContactScreenNameEnum.DEVICE_WRITE.getCode())) {
                orginDomain = JSON.parseObject(JSON.toJSONString(o), new TypeReference<ContactScreenDomain<ScreenMqttDeviceControlDTO>>() {
                });
            } else if (StringUtils.equals(operateName, ContactScreenNameEnum.DEVICE_STATUS_READ.getCode())) {
                orginDomain = JSON.parseObject(JSON.toJSONString(o), new TypeReference<ContactScreenDomain<ScreenMqttDeviceStatusReadDTO>>() {
                });
            } else if (StringUtils.equals(operateName, ContactScreenNameEnum.FAMILY_SCENE_SET.getCode())) {
                orginDomain = JSON.parseObject(JSON.toJSONString(o), new TypeReference<ContactScreenDomain<ScreenMqttSceneControlDTO>>() {
                });
            } else if (StringUtils.equals(operateName, ContactScreenNameEnum.FAMILY_CONFIG_UPDATE.getCode())) {
                orginDomain = JSON.parseObject(JSON.toJSONString(o), new TypeReference<ContactScreenDomain<ScreenMqttConfigUpdateDTO>>() {
                });
            } else {
                orginDomain = JSON.parseObject(JSON.toJSONString(o), new TypeReference<ContactScreenDomain<ScreenMqttBaseDTO>>() {
                });

            }
        }

        return orginDomain;
    }

    @Override
    public void update(Observable o, Object arg) {
        mqttCloudToScreenTimeoutObserverService.mvTask(queue);
    }
}
