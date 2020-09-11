package com.landleaf.homeauto.center.device.model.vo.scene;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="WebSceneDetailDeviceActionVO", description="查看场景非暖通设备动作详情信息")
public class WebSceneDetailDeviceActionVO {

    @ApiModelProperty(value = "设备号")
    private String deviceSn;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "楼层名称")
    private String floorName;

    @ApiModelProperty(value = "房间名称")
    private String roomName;

    @ApiModelProperty(value = "属性信息")
    private List<WebSceneDetailAttributeVO> attributeVOS;

}
