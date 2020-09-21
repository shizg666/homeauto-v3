package com.landleaf.homeauto.common.domain.websocket;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Yujiumin
 * @version 2020/9/15
 */
@Data
@AllArgsConstructor
@ApiModel("心跳消息")
public class HeartbeatMessage {

    @ApiModelProperty("心跳")
    private String heartbeat;

}
