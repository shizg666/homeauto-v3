package com.landleaf.homeauto.center.device.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 暖通设备信息对象
 *
 * @author Yujiumin
 * @version 2020/9/30
 */
@Data
@NoArgsConstructor
@ApiModel("暖通设备信息业务对象")
public class HvacDeviceBO {

    @ApiModelProperty("设备ID")
    private String deviceId;

    @ApiModelProperty("暖通运行模式")
    private Integer mode;

}
