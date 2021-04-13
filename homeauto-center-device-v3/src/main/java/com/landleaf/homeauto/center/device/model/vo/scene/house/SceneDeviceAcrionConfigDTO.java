package com.landleaf.homeauto.center.device.model.vo.scene.house;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="属性信息对象")
public class SceneDeviceAcrionConfigDTO {

    @ApiModelProperty(value = "属性code")
    private String  code;

    @ApiModelProperty(value = "配置的值(为null则该属性没配置)")
    private String attributeVal;

}
