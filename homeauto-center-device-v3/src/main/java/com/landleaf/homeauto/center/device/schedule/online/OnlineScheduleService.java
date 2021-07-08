package com.landleaf.homeauto.center.device.schedule.online;

import cn.jiguang.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.cache.ConfigCacheProvider;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenFamilyBO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.online.FamilyScreenOnline;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyScreenOnlineService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.common.mqtt.MqttClientInfo;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Autowired
    private IHomeAutoFamilyService familyService;

    /**
     * 每10分钟检查mqtt客户端，并进行更新
     */
    @Scheduled(cron = "0 0/5 * * * ? ")
    public void updateMqttClients() {
        if (!checkOnline) {
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

            List<String> mqttClientMacs = mqttClientInfos.stream().map(i -> i.getClientid()).collect(Collectors.toList());

            List<FamilyScreenOnline> screenOnlineList = Lists.newArrayList();
            //查询所有的家庭
            List<HomeAutoFamilyDO> allFamily = familyService.list();
            if (CollectionUtils.isEmpty(allFamily)) {
                return;
            }
            List<String> allFamilyMacs = allFamily.stream().filter(i -> {
                if (!StringUtil.isEmpty(i.getScreenMac())) {
                    return true;
                }
                return false;
            }).map(i -> i.getScreenMac()).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(allFamilyMacs)) {
                return;
            }
            List<HomeAutoFamilyDO> excludeFamily = allFamily.stream().filter(i -> {
                if (StringUtils.isEmpty(i.getScreenMac())) {
                    return false;
                }
                if (CollectionUtils.isEmpty(mqttClientMacs)) {
                    return true;
                }
                if (!mqttClientMacs.contains(i.getScreenMac())) {
                    return true;
                }
                return false;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(mqttClientInfos)) {
                for (MqttClientInfo clientInfo : mqttClientInfos) {
                    if (!allFamilyMacs.contains(clientInfo.getClientid())) {
                        continue;
                    }
                    String clientid = clientInfo.getClientid();
                    boolean connected = clientInfo.isConnected();
                    FamilyScreenOnline data = new FamilyScreenOnline();
                    data.setScreenMac(clientid);
                    data.setStatus(connected ? 1 : 0);
                    ScreenFamilyBO familyInfoByMac = configCacheProvider.getFamilyInfoByMac(clientid);
                    if (familyInfoByMac != null) {
                        data.setFamilyId(familyInfoByMac.getId());
                    }
                    screenOnlineList.add(data);
                }
            }
            if (!CollectionUtils.isEmpty(excludeFamily)) {
                screenOnlineList.addAll(excludeFamily.stream().map(i -> {
                    FamilyScreenOnline data = new FamilyScreenOnline();
                    data.setScreenMac(i.getScreenMac());
                    data.setStatus(0);
                    data.setFamilyId(i.getId());
                    return data;
                }).collect(Collectors.toList()));
            }
            //还要加上未统计到的大屏mac
            familyScreenOnlineService.updateStatus(screenOnlineList);
            // 没做更新的，默认改为离线

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
