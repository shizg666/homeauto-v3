package com.landleaf.homeauto.common.domain.websocket;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 设备状态数据值对象
 *
 * @author Yujiumin
 * @version 2020/9/4
 */
@Data
@NoArgsConstructor
@ApiModel("设备状态数据值对象")
public class DeviceStatusMessage {

    @ApiModelProperty("家庭ID")
    private Long familyId;

    @ApiModelProperty("设备ID")
    private Long deviceId;

    @ApiModelProperty("品类")
    private String category;
    @ApiModelProperty("产品")
    private String productCode;


    @ApiModelProperty("设备属性")
    private Map<String, Object> attributes;

}
