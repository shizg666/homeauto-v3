package com.landleaf.homeauto.contact.screen.service.impl;

import com.landleaf.homeauto.common.constant.TimeConst;
import com.landleaf.homeauto.common.constant.enums.QosEnumConst;
import com.landleaf.homeauto.common.constant.enums.TopicEnumConst;
import com.landleaf.homeauto.common.mqtt.Client;
import com.landleaf.homeauto.common.mqtt.MqttFactory;
import com.landleaf.homeauto.contact.screen.service.MqttConnCheckService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 查看mqtt链接是否无误的逻辑实现
 * @author wenyilu
 */
@Service
@Slf4j
public class MqttConnCheckServiceImpl implements MqttConnCheckService {

    @Autowired
    private MqttFactory mqttFactory;

    private Client client = null;

    private static long LAST_MODIFY = System.currentTimeMillis();

    /**
     * {@inheritDoc}
     * <p>
     * 查看链接是否无误，通过监听mqtt接口推送一条心跳消息实现
     */
    @Override
    public boolean checkLink() {
        client = mqttFactory.getClient(false);
        // 默认1分钟以内存在心跳则认为链接是存在的
//        if (TimeConst.MILLISECONDS_PER_MINUTE < System.currentTimeMillis() - LAST_MODIFY) {
            // 发送一条心跳消息
            client.pubTopic(TopicEnumConst.CHECK_CONN_TOPIC.getTopic(), StringUtils.EMPTY, QosEnumConst.QOS_0);
            try {
                Thread.sleep(5000L);
            } catch (IllegalArgumentException | InterruptedException e) {
                log.error("线程休眠用于等到mqtt响应失败。");
            }
            return TimeConst.MILLISECONDS_PER_MINUTE >= System.currentTimeMillis() - LAST_MODIFY;
//        }
//        return true;
    }

    @Override
    public void refresh() {
        log.info("心跳检测成功,刷新时间：{}",DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        LAST_MODIFY = System.currentTimeMillis();
    }
}
