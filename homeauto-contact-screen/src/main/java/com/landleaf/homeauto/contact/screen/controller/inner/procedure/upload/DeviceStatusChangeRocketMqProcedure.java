package com.landleaf.homeauto.contact.screen.controller.inner.procedure.upload;

import com.landleaf.homeauto.common.domain.dto.screen.mqtt.upload.ScreenMqttUploadBaseDTO;
import org.springframework.stereotype.Component;

/**
 * 状态变化往上报
 *
 * @author wenyilu
 */
@Component
public class DeviceStatusChangeRocketMqProcedure extends AbstractUploadRocketMqProcedure {

    @Override
    public void procedureMessage(ScreenMqttUploadBaseDTO screenUploadBaseDTO) {

    }
}
