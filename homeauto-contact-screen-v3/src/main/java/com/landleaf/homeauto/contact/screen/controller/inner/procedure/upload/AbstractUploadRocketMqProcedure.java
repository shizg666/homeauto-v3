package com.landleaf.homeauto.contact.screen.controller.inner.procedure.upload;

import com.landleaf.homeauto.common.domain.dto.screen.mqtt.upload.ScreenMqttUploadBaseDTO;

/**
 * @ClassName AbstractResponseRocketMqProcedure
 * @Description: TODO
 * @Author wyl
 * @Date 2020/8/11
 * @Version V1.0
 **/
public abstract class AbstractUploadRocketMqProcedure {

    public abstract void procedureMessage(ScreenMqttUploadBaseDTO screenUploadBaseDTO);
}
