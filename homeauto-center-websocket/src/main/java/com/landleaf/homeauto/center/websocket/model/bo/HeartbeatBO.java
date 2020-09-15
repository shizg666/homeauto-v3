package com.landleaf.homeauto.center.websocket.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Yujiumin
 * @version 2020/9/15
 */
@Data
@ApiModel("心态业务对象")
public class HeartbeatBO {

    @ApiModelProperty("失联次数")
    private Integer times;

    @ApiModelProperty("上次联络时间")
    private Long timestamp;

}
