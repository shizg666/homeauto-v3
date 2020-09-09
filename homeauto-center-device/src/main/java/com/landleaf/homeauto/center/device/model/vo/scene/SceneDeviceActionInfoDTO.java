package com.landleaf.homeauto.center.device.model.vo.scene;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="SceneDeviceActionInfoDTO", description="场景非暖通设备动作详情信息")
public class SceneDeviceActionInfoDTO {

    @ApiModelProperty(value = "设备号")
    private String deviceSn;

    @ApiModelProperty(value = "属性id")
    private String attributeId;

    @ApiModelProperty(value = "属性值")
    private String  val;

    @ApiModelProperty(value = "判断：eq 等于，gt 大于，egt 大于等于，lt 小于，elt 小于等于")
    private Integer  operator;
}
