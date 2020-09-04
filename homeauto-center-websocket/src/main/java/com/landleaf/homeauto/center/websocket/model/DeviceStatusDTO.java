package com.landleaf.homeauto.center.websocket.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设备状态数据传输对象
 *
 * @author Yujiumin
 * @version 2020/9/4
 */
@Data
@NoArgsConstructor
@ApiModel("设备状态数据传输对象")
public class DeviceStatusDTO {

    @ApiModelProperty("家庭ID")
    private String familyId;

    @ApiModelProperty("品类Code")
    private String category;

    @ApiModelProperty("设备序列号")
    private String deviceSn;

    @ApiModelProperty("属性状态名")
    private String attributeName;

    @ApiModelProperty("属性状态值")
    private Object attributeValue;

}
