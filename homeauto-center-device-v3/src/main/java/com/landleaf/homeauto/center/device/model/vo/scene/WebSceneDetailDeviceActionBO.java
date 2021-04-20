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
@ApiModel(value="WebSceneDetailDeviceActionBO", description="查看-场景非暖通设备动作详情信息")
public class WebSceneDetailDeviceActionBO {

    @ApiModelProperty(value = "设备id")
    private Long deviceId;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "设备号")
    private String deviceSn;

    @ApiModelProperty(value = "产品id")
    private Long productId;

    @ApiModelProperty(value = "产品code")
    private String productCode;


    @ApiModelProperty(value = "楼层名称")
    private String floor;

    @ApiModelProperty(value = "房间名称")
    private String roomName;

    @ApiModelProperty(value = "属性配置")
    private List<SceneAttributeInfoBO> actions;

//    @ApiModelProperty(value = "属性id")
//    private String attributeId;
//
//    @ApiModelProperty(value = "选择属性值")
//    private String val;

}
