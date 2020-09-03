package com.landleaf.homeauto.contact.screen.handle.mqtt.to;

import com.alibaba.druid.util.StringUtils;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.request.ScreenMqttDeviceControlDTO;
import com.landleaf.homeauto.contact.screen.common.enums.AckCodeTypeEnum;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenNameEnum;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenDomain;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenMqttRequest;
import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenDeviceAttribute;
import com.landleaf.homeauto.contact.screen.dto.payload.mqtt.request.DeviceWritePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

/**
 * 云端控制设备处理类
 *
 * @author wenyilu
 */
@Slf4j
@Component
public class DeviceWriteHandle implements Observer {

    @Autowired
    private RequestHandleCommonUtil requestHandleCommonUtil;
    @Override
    @Async("cloudToScreenMessageExecute")
    public void update(Observable o, Object arg) {
        ContactScreenDomain message = (ContactScreenDomain) arg;
        // 走下面处理逻辑
        String operateName = message.getOperateName();
        if (StringUtils.equals(operateName, ContactScreenNameEnum.DEVICE_WRITE.getCode())) {
            requestHandleCommonUtil.sendMsg(message, operateName);
        }

    }

}
