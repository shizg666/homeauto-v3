package com.landleaf.homeauto.common.domain.dto.screen.mqtt.request;

import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
import lombok.Data;

/**
 * @ClassName ScreenConfigUpdateDTO
 * @Description: 配置更新通知DTO
 * @Author wyl
 * @Date 2020/8/10
 * @Version V1.0
 **/
@Data
public class ScreenMqttConfigUpdateDTO extends ScreenMqttBaseDTO {


    /**
     * 更新操作类型 scene
     * {@link ContactScreenConfigUpdateTypeEnum}
     */
    private String updateType;
}
