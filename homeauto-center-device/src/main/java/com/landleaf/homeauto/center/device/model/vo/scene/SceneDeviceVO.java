package com.landleaf.homeauto.center.device.model.vo.scene;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/9/7
 */
@Data
@ApiModel(value="SceneDeviceVO", description="场景设备信息")
public class SceneDeviceVO {

    @ApiModelProperty("设备id")
    private String id;

    @ApiModelProperty("设备号")
    private String deviceSn;

    @ApiModelProperty("设备名称")
    private String name;

    @ApiModelProperty("房间名称")
    private String roomName;

    @ApiModelProperty("楼层名称")
    private String floorName;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "产品名称")
    private String productId;

    @ApiModelProperty(value = "设备属性集合")
    private List<SceneDeviceAttributeVO> attributes;





}
