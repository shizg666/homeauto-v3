package com.landleaf.homeauto.contact.screen.handle.mqtt.to;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.common.constant.enums.QosEnumConst;
import com.landleaf.homeauto.common.constant.enums.TopicEnumConst;
import com.landleaf.homeauto.common.mqtt.SyncSendUtil;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenMqttResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 大屏端通过mqtt与云端通讯:响应处理类
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class MqttCommonResponseHandle {


    @Autowired(required = false)
    private SyncSendUtil syncSendUtil;

    public void handlerRequest(ContactScreenMqttResponse response) {

        String screenMac = ContactScreenContext.getContext().getScreenMac();

        syncSendUtil.pubTopic(TopicEnumConst.CONTACT_SCREEN_CLOUD_TO_SCREEN.getTopic().concat(screenMac), JSON.toJSONString(response), QosEnumConst.QOS_0);


        log.info("[下发外部mqtt消息执行]:消息类别:[{}],外部消息编号:[{}],消息体:{}",
                response.getHeader().getName(), response.getHeader().getMessageId(), JSON.toJSONString(response));

    }

}
