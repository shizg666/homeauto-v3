package com.landleaf.homeauto.contact.screen.scedule;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.common.mqtt.*;
import com.landleaf.homeauto.common.mqtt.annotation.MqttTopic;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.contact.screen.service.MqttConnCheckService;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.landleaf.homeauto.common.constant.RedisCacheConst.*;

/**
 * @author pilo
 */
@Component
public class ScheduleService {

    private static Logger logger = LoggerFactory.getLogger(ScheduleService.class);

    @Resource
    private MqttConnCheckService mqttConnCheckServiceImpl;

    @Autowired
    private MqttFactory mqttFactory;

    @Resource
    private List<MessageBaseHandle> list;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private MqttConfigProperty mqttConfigProperty;

    /**
     * 每1分钟检查mqtt链接，如果链接已断开则重新链接
     */
    @Scheduled(cron = "0/20 * * * * *")
    public void checkMqttConn() {
        boolean conn = mqttConnCheckServiceImpl.checkLink();
        if (!conn) {
            logger.info("检查链接失败，重新链接,时间为  {}", DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));

            Client client = mqttFactory.reconnect(true);
            if (!CollectionUtils.isEmpty(list)) {
                list.forEach(i -> {
                    MqttTopic mt = null;
                    if (null != (mt = i.getClass().getAnnotation(MqttTopic.class))) {
                        if (!mt.omitted()) {
                            client.subTopic(mt.topic());
                            logger.info("订阅topic:{}", mt.topic());
                        }
                    }
                });
            }
        }
    }


    /**
     * 每2分钟检查mqtt客户端，并进行更新
     */
    @Scheduled(cron = "0 0/10 * * * ? ")
    public void updateMqttClients() {
        try {
            String url = mqttConfigProperty.getHttpUrl();
            Boolean enable = mqttConfigProperty.getHttpEnable();
            String user = mqttConfigProperty.getHttpUser();
            String password = mqttConfigProperty.getHttpPassword();
            logger.info("url:{},enalbe:{},user:{},password:{}",url,enable,user,password);
            if (!enable){
                return;
            }
            logger.info("2分钟定时校验mqtt客户端是否在线~~~");
            String result2 = HttpRequest.get(url).timeout(20000)
                    .basicAuth(user, password).execute().body();

            if (!StringUtils.isEmpty(result2)) {

                Object dataObject = JSON.parseObject(result2).get("data");

                List<MqttClientInfo> mqttClientInfoList = JSON.parseArray(dataObject.toString(), MqttClientInfo.class);
                if(!CollectionUtils.isEmpty(mqttClientInfoList)){
                    logger.info("mqtt客户端数量:{}",mqttClientInfoList.size());
                }
                //保存3分鐘
                mqttClientInfoList.forEach(
                        s->redisUtils.hsetEx(CONTACT_SCREEN_MQTT_CLIENT_STATUS,s.getClientid(),JSON.toJSONString(s),THIRD_COMMON_EXPIRE));
            }

            Set hkeys = redisUtils.hmkeys(CONTACT_SCREEN_MQTT_CLIENT_STATUS);

            hkeys.forEach(s->redisUtils.hgetEx(CONTACT_SCREEN_MQTT_CLIENT_STATUS,(String)s));//过期清理

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
