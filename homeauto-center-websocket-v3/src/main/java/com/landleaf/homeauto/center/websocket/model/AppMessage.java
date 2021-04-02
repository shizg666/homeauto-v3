package com.landleaf.homeauto.center.websocket.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yujiumin
 * @version 2020/9/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("推送消息模板")
public class AppMessage {

    @ApiModelProperty("消息码")
    private Integer messageCode;

    @ApiModelProperty("消息内容")
    private Object message;
}
