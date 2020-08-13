package com.landleaf.homeauto.contact.screen.client.dto.payload;

/**
 * @ClassName MqttScreenDetailError
 * @Description: 错误详情
 * @Author wyl
 * @Date 2020/7/31
 * @Version V1.0
 **/
public class ContactScreenCommonDetailReply {

    /**
     * uiqueId 唯一标记
     */
    private String uniqueId;

    /**
     * code 状态码
     */
    private Integer code;

    /**
     * message 信息
     */
    private String message;
}
