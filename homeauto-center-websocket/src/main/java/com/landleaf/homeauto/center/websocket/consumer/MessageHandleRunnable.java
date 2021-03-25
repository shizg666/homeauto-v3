package com.landleaf.homeauto.center.websocket.consumer;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.websocket.model.AppMessage;
import com.landleaf.homeauto.center.websocket.model.WebSocketSessionContext;
import com.landleaf.homeauto.center.websocket.rocketmq.util.CollectionUtils;
import com.landleaf.homeauto.common.domain.websocket.MessageModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.yeauty.pojo.Session;

import java.util.Map;

/**
 * @ClassName MessageHandleRunnable
 * @Description: TODO
 * @Author wyl
 * @Date 2021/3/18
 * @Version V1.0
 **/
@Slf4j
public class MessageHandleRunnable implements Runnable{

    private MessageModel messageModel;
    private String key;

    public MessageHandleRunnable(MessageModel messageModel, String key) {
        this.messageModel = messageModel;
        this.key = key;
    }

    @Override
    public void run() {
        //先清理家庭连接,再推送
        WebSocketSessionContext.clearLink(key);
        Map<String, Session> sessionMap = WebSocketSessionContext.get(key);
        if (CollectionUtils.isEmpty(sessionMap)) {
            log.info("家庭无在线连接,本次不推送：{}", key);
            return;
        }
        for (Map.Entry<String, Session> entry : sessionMap.entrySet()) {
            AppMessage appMessage = new AppMessage(messageModel.getMessageCode(), messageModel.getMessage());
            String appMessageJsonString = JSON.toJSONString(appMessage);
            try {
                entry.getValue().sendText(appMessageJsonString);
                log.info("成功推送消息,familyId:{},sessionId:{}",key,entry.getValue().id().asLongText());
            } catch (Exception e) {
                log.error("发送消息异常了,我又该肿么办....");
            }
        }
    }
}
