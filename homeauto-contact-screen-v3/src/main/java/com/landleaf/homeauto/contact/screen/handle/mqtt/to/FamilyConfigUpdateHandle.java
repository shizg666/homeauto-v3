package com.landleaf.homeauto.contact.screen.handle.mqtt.to;

import com.alibaba.druid.util.StringUtils;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.request.ScreenMqttConfigUpdateDTO;
import com.landleaf.homeauto.contact.screen.common.enums.AckCodeTypeEnum;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenNameEnum;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenDomain;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenMqttRequest;
import com.landleaf.homeauto.contact.screen.dto.payload.mqtt.request.FamilyConfigUpdatePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Observable;
import java.util.Observer;

/**
 * 配置更新通知处理类
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class FamilyConfigUpdateHandle implements Observer {

    @Autowired
    private RequestHandleCommonUtil requestHandleCommonUtil;

    @Override
    @Async("cloudToScreenMessageExecute")
    public void update(Observable o, Object arg) {
        ContactScreenDomain message = (ContactScreenDomain) arg;
        // 走下面处理逻辑
        String operateName = message.getOperateName();
        if (StringUtils.equals(operateName, ContactScreenNameEnum.FAMILY_CONFIG_UPDATE.getCode())) {
            requestHandleCommonUtil.sendMsg(message, operateName);
        }
    }

}
