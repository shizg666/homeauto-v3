package com.landleaf.homeauto.center.websocket.model.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Yujiumin
 * @version 2020/9/15
 */
@Data
@ApiModel("心跳消息")
public class HeartbeatMessage {

    @ApiModelProperty("心跳")
    private String heartbeat;

}
