package com.landleaf.homeauto.center.device.model.dto.jhappletes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="JZSceneDeviceActionDTO", description="场景动作配置")
public class JZSceneDeviceActionDTO {

    @ApiModelProperty(value = "属性code")
    private String attributeCode;

    @ApiModelProperty(value = "属性值")
    private String  val;
}
