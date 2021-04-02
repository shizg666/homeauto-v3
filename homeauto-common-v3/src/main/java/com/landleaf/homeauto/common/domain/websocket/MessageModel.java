package com.landleaf.homeauto.common.domain.websocket;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yujiumin
 * @version 2020/9/21
 */
@Data
@NoArgsConstructor
public class MessageModel {

    @ApiModelProperty("消息类型")
    private Integer messageCode;

    @ApiModelProperty("家庭ID")
    private String familyId;

    @ApiModelProperty("消息内容")
    private Object message;

    public MessageModel(MessageEnum messageEnum, String familyId, Object message) {
        this.messageCode = messageEnum.code;
        this.familyId = familyId;
        this.message = message;
    }
}
