package com.landleaf.homeauto.center.websocket.model;

import com.landleaf.homeauto.center.websocket.constant.MessageEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Yujiumin
 * @version 2020/9/10
 */
@Data
@ApiModel("推送消息模板")
public class WebSocketMessageModel {

    @ApiModelProperty("消息码")
    private Integer messageCode;

    @ApiModelProperty("消息内容")
    private Object message;

    public WebSocketMessageModel(MessageEnum messageEnum, Object message) {
        this.messageCode = messageEnum.code();
        this.message = message;
    }
}
