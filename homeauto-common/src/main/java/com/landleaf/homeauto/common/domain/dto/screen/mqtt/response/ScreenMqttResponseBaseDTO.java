package com.landleaf.homeauto.common.domain.dto.screen.mqtt.response;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName ScreenBaseDTO
 * @Description: 内部服务下发数据到大屏返回基类
 * @Author wyl
 * @Date 2020/8/10
 * @Version V1.0
 **/
@Data
public class ScreenMqttResponseBaseDTO {
    /**
     * 消息Id
     */
    private String messageId;

    /**
     * 家庭编码
     */
    private String familyCode;
    /**
     * 大屏mac
     */
    private String screenMac;
    /**
     * 家庭方案
     */
    private String familyScheme;

    /**
     * 状态码
     */
    private Integer code;
    /**
     * 成功或错误信息
     */
    private String message;


}
