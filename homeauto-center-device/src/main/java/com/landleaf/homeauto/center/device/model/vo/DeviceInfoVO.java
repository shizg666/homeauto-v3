package com.landleaf.homeauto.center.device.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yujiumin
 * @version 2020/8/24
 */
@Data
@NoArgsConstructor
@ApiModel(value="DeviceInfoVO", description="DeviceInfoVO")
public class DeviceInfoVO {

    @ApiModelProperty("设备id")
    private String id;

    @ApiModelProperty("设备名称")
    private String name;

    @ApiModelProperty("设备图片")
    private String icon;



}
