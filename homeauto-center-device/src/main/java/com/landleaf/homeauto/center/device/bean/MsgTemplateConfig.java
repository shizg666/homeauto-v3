package com.landleaf.homeauto.center.device.bean;

import com.landleaf.homeauto.center.device.service.mybatis.IMsgTemplateService;
import com.landleaf.homeauto.common.domain.po.device.MsgTemplate;
import com.landleaf.homeauto.common.enums.msg.MsgTemplateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wenyilu
 */
@Configuration
public class MsgTemplateConfig {

    private IMsgTemplateService msgTemplateService;

    public static Map<Integer, MsgTemplate> EMAIL_MSG_HOLDER = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        List<MsgTemplate> msgTemplates = msgTemplateService.list();
        msgTemplates.forEach(m -> {
            //系统类消息模板
            if (MsgTemplateEnum.MSG_SYS.getType().equals(m.getTempType())) {

            }

            //手机类消息模板
            if (MsgTemplateEnum.MSG_MOBILE.getType().equals(m.getTempType())) {

            }

            //邮件类消息模板
            if (MsgTemplateEnum.MSG_EMAIL.getType().equals(m.getTempType())) {
                EMAIL_MSG_HOLDER.put(m.getMsgType(), m);
            }

            //推送类消息模板
            if (MsgTemplateEnum.MSG_PUSH.getType().equals(m.getTempType())) {

            }
        });
    }

    @Autowired
    public void setMsgTemplateService(IMsgTemplateService msgTemplateService) {
        this.msgTemplateService = msgTemplateService;
    }
}
