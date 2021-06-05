package com.landleaf.homeauto.common.domain.dto.device.status;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 设备基本状态表(暖通、数值、在线离线标记)
 */
@Data
@NoArgsConstructor
@ApiModel("设备基本状态表(暖通、数值、在线离线标记)")
public class ScreenDeviceFaultCurrentDetailDTO {

    @ApiModelProperty(value = "故障编码")
    private String code;
    @ApiModelProperty(value = "故障值")
    private String value;
    @ApiModelProperty(value = "故障类型(1:二进制故障，2：数值异常，3：通信)")
    private Integer type;


}
