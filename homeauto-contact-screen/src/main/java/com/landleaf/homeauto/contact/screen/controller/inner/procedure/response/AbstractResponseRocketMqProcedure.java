package com.landleaf.homeauto.contact.screen.controller.inner.procedure.response;

import com.landleaf.homeauto.common.domain.dto.screen.mqtt.response.ScreenMqttResponseBaseDTO;

/**
 * @ClassName AbstractResponseRocketMqProcedure
 * @Description: TODO
 * @Author wyl
 * @Date 2020/8/11
 * @Version V1.0
 **/
public abstract class AbstractResponseRocketMqProcedure {

    public abstract void procedureMessage(ScreenMqttResponseBaseDTO screenResponseBaseDTO);
}
