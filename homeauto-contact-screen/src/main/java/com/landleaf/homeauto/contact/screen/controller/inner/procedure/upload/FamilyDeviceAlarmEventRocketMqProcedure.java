package com.landleaf.homeauto.contact.screen.controller.inner.procedure.upload;

import com.landleaf.homeauto.common.domain.dto.screen.mqtt.upload.ScreenMqttUploadBaseDTO;
import org.springframework.stereotype.Component;

/**
 * 安防报警事件往上报
 *
 * @author wenyilu
 */
@Component
public class FamilyDeviceAlarmEventRocketMqProcedure extends AbstractUploadRocketMqProcedure {
    @Override
    public void procedureMessage(ScreenMqttUploadBaseDTO screenUploadBaseDTO) {

    }
}
