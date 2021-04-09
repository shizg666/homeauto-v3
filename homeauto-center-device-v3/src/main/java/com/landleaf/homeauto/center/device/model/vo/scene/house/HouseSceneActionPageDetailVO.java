package com.landleaf.homeauto.center.device.model.vo.scene.house;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="HouseSceneActionPageDetailVO", description="")
public class HouseSceneActionPageDetailVO {


//    @ApiModelProperty(value = "设备id")
//    private String id;

    @ApiModelProperty(value = "设备id")
    private String deviceId;

    @ApiModelProperty(value = "设备名称")
    private String name;

    @ApiModelProperty(value = "属性")
    private String attributes;
}
