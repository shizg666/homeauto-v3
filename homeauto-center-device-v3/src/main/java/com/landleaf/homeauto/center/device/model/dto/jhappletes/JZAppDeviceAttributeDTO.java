package com.landleaf.homeauto.center.device.model.dto.jhappletes;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wenyilu
 * @ClassName 设备属性
 **/
@Data
public class JZAppDeviceAttributeDTO {

    @ApiModelProperty("属性code")
    private String code;

    @ApiModelProperty("属性值")
    private String value;

}
