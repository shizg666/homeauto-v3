package com.landleaf.homeauto.center.device.eventbus.converter;

import cn.hutool.core.collection.CollectionUtil;
import com.landleaf.homeauto.center.device.eventbus.event.SendCodeEvent;
import com.landleaf.homeauto.common.domain.po.device.JsmsSendHistory;
import com.landleaf.homeauto.common.util.ObjectWrapper;

/**
 * JsmsSendHistory类型转换工具
 */
public class JsmsSendHistoryConverter {

    /**
     * 由发送验证码时间转换为入库对象
     *
     * @param event
     * @return
     */
    public static JsmsSendHistory serializeFromEvent(SendCodeEvent event) {
        JsmsSendHistory jsmsSendHistory = new JsmsSendHistory();
        jsmsSendHistory.setMessageId(event.messageId());
        jsmsSendHistory.setTempId(event.shSmsMsg().smsMsgType().getTempId());
        jsmsSendHistory.setMobile(event.shSmsMsg().mobile());
        jsmsSendHistory.setContent(getMsgContent(event));
        jsmsSendHistory.setTtl(event.shSmsMsg().smsMsgType().getTtl());
        jsmsSendHistory.setSendTime(event.occurredTime());
        return jsmsSendHistory;
    }


    private static String getMsgContent(SendCodeEvent event) {
        String msgContent = event.shSmsMsg().smsMsgType().getMsgContent();
        ObjectWrapper wrapper = new ObjectWrapper(msgContent);
        if (CollectionUtil.isNotEmpty(event.shSmsMsg().tempParaMap())) {
            event.shSmsMsg().tempParaMap().forEach((k, v) -> wrapper.s(wrapper.s().replace("{{" + k + "}}", v)));
            msgContent = wrapper.s();
        } else {
            msgContent = msgContent.replace("{{code}}", event.shSmsMsg().code())
                    .replace("{{time}}", String.valueOf(event.shSmsMsg().smsMsgType().getTtl() / event.shSmsMsg().smsMsgType().getSecondTimeUnitType().getSeconds()));
        }
        return msgContent;
    }
}
