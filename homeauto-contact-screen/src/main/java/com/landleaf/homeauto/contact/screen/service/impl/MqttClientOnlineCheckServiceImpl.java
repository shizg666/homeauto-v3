package com.landleaf.homeauto.contact.screen.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.enums.screen.MqttCallBackTypeEnum;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.contact.screen.service.MqttClientOnlineCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName MqttClientOnlineCheckService
 * @Description: mqtt客户端是否在线校验
 * @Author wyl
 * @Date 2020/9/3
 * @Version V1.0
 **/
@Service
public class MqttClientOnlineCheckServiceImpl implements MqttClientOnlineCheckService {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Boolean checkClientOnline(String screenMac) {
        if (redisUtils.hasKey(RedisCacheConst.CONTACT_SCREEN_MQTT_CLIENT_ONLINE_STATUS)) {
            Object hget = redisUtils.hget(RedisCacheConst.CONTACT_SCREEN_MQTT_CLIENT_ONLINE_STATUS, screenMac);
            if (hget != null) {
                String action = (String) hget;
                if (!StringUtils.isEmpty(action) && StringUtils.equals(action, MqttCallBackTypeEnum.CLIENT_DISCONNECTED.code)) {
                    return false;
                }
            }
        }
        return true;
    }
}
