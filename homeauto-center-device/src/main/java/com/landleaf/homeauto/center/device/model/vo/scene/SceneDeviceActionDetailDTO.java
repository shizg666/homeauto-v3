package com.landleaf.homeauto.center.device.model.vo.scene;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="SceneDeviceActionDetailDTO", description="SceneDeviceActionDetailDTO")
public class SceneDeviceActionDetailDTO {

    @ApiModelProperty(value = "属性id")
    private String attributeId;

    @ApiModelProperty(value = "属性值")
    private String  val;

    @ApiModelProperty(value = "判断：0 等于，1 大于，2 大于等于，-1 小于，-2 小于等于")
    private Integer  operator;
}
