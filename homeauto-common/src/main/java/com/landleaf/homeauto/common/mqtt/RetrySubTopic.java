package com.landleaf.homeauto.common.mqtt;

import com.landleaf.homeauto.common.annotation.MqttTopic;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName RetrySubTopic
 * @Description: 重新订阅主题
 * @Author shizg
 * @Date 2020/4/3
 * @Version V1.0
 **/
@Component
public class RetrySubTopic {

    @Resource
    private MqttFactory mqttFactory;

    /**
     * 异步订阅主题
     *
     * @param list mqtt 主题处理类集合
     */
    @Async
    public void reSubTopic(List<MessageBaseHandle> list, String clientId) {
        Client client = mqttFactory.getClient(false);
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(i -> {
                MqttTopic mt = null;
                if (null != (mt = i.getClass().getAnnotation(MqttTopic.class))) {

                    if (!mt.omitted()) {
                        String topic = mt.topic();
                        if (topic.endsWith("${id}")) {
                            // 特殊的标志位，需要替换为对应的clientId
                            topic = topic.replace("${id}", clientId);
                        }
                        client.subTopic(topic);
                    }
                }
            });
        }
    }
}
