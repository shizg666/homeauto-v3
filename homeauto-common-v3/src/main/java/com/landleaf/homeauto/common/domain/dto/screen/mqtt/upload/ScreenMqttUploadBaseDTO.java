package com.landleaf.homeauto.common.domain.dto.screen.mqtt.upload;

import lombok.Data;

/**
 * @ClassName ScreenBaseDTO
 * @Description: 大屏上报云端基类
 * @Author wyl
 * @Date 2020/8/10
 * @Version V1.0
 **/
@Data
public class ScreenMqttUploadBaseDTO {
    /**
     * 消息Id
     */
    private String messageId;

    /**
     * 大屏mac
     */
    private String screenMac;


}
