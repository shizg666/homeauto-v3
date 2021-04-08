package com.landleaf.homeauto.center.device.model.vo.scene.house;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HouseSceneActionConfigDTO", description="")
public class HouseSceneActionConfigDTO {

    @ApiModelProperty(value = "属性code")
    private String attributeCode;

    @ApiModelProperty(value = "属性值")
    private String  val;
}
