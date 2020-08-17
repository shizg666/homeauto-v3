package com.landleaf.homeauto.common.domain.dto.screen.mqtt.request;

import lombok.Data;

/**
 * @ClassName ScreenBaseDTO
 * @Description: 内部服务下发数据到大屏基类
 * @Author wyl
 * @Date 2020/8/10
 * @Version V1.0
 **/
@Data
public class ScreenMqttBaseDTO {
    /**
     * 消息Id(内部)
     */
    private String messageId;
    /**
     * 大屏mac
     */
    private String screenMac;


}
