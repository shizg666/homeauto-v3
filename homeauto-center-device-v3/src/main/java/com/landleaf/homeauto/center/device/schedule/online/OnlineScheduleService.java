package com.landleaf.homeauto.center.device.schedule.online;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.cache.ConfigCacheProvider;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenFamilyBO;
import com.landleaf.homeauto.center.device.model.domain.online.FamilyScreenOnline;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyScreenOnlineService;
import com.landleaf.homeauto.common.mqtt.MqttClientInfo;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.landleaf.homeauto.common.constant.RedisCacheConst.CONTACT_SCREEN_MQTT_CLIENT_STATUS;

/**
 * @author pilo
 */
@Slf4j
@Component
public class OnlineScheduleService {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private IFamilyScreenOnlineService familyScreenOnlineService;
    @Autowired
    private ConfigCacheProvider configCacheProvider;
    @Value(value = "${homeauto.screen.online.check:false}")
    private Boolean checkOnline;
    /**
     * 每10分钟检查mqtt客户端，并进行更新
     */
    @Scheduled(cron = "0 0/10 * * * ? ")
    public void updateMqttClients() {
        if(!checkOnline){
            return;
        }
        try {
            List<MqttClientInfo> mqttClientInfos = Lists.newArrayList();
            Set hkeys = redisUtils.hmkeys(CONTACT_SCREEN_MQTT_CLIENT_STATUS);

            hkeys.forEach(s -> {
                Object ex = redisUtils.hgetEx(CONTACT_SCREEN_MQTT_CLIENT_STATUS, (String) s);
                if (!Objects.isNull(ex)) {
                    MqttClientInfo mqttClientInfo = JSON.parseObject(((String) (ex)), MqttClientInfo.class);
                    mqttClientInfos.add(mqttClientInfo);
                }
            });
            if(!CollectionUtils.isEmpty(mqttClientInfos)){
                List<FamilyScreenOnline> screenOnlineList = Lists.newArrayList();
                for (MqttClientInfo clientInfo : mqttClientInfos) {
                    String clientid = clientInfo.getClientid();
                    boolean connected = clientInfo.isConnected();
                    FamilyScreenOnline data = new FamilyScreenOnline();
                    data.setScreenMac(clientid);
                    data.setStatus(connected ? 1 : 0);
                    if (clientid.contains("homeauto-contact-screen")) {
                        continue;
                    }
                        ScreenFamilyBO familyInfoByMac = configCacheProvider.getFamilyInfoByMac(clientid);
                        if (familyInfoByMac != null) {
                            data.setFamilyId(familyInfoByMac.getId());
                        }
                    screenOnlineList.add(data);
                }
                familyScreenOnlineService.updateStatus(screenOnlineList);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
