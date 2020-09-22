package com.landleaf.homeauto.contact.screen.client.listener;

import com.landleaf.homeauto.common.mqtt.Client;
import com.landleaf.homeauto.common.mqtt.MessageBaseHandle;
import com.landleaf.homeauto.common.mqtt.MqttFactory;
import com.landleaf.homeauto.common.mqtt.annotation.MqttTopic;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author wenyilu
 */
@Component
public class ScheduleService {

    private static Logger logger = LoggerFactory.getLogger(ScheduleService.class);

    @Autowired
    private MqttConnCheckService mqttConnCheckService;

    @Autowired
    private MqttFactory mqttFactory;

    @Resource
    private List<MessageBaseHandle> list;

    /**
     * 每1分钟检查mqtt链接，如果链接已断开则重新链接
     */
    @Scheduled(cron = "0 0/1 * * * *")
    public void checkMqttConn() {
        boolean conn = mqttConnCheckService.checkLink();
        if (!conn) {
            logger.info("检查链接失败，重新链接,时间为  {}", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            Client client = mqttFactory.reconnect(false);
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


}
