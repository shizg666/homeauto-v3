package com.landleaf.homeauto.common.domain.dto.screen;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wenyilu
 * @ClassName 设备属性
 **/
@Data
public class ScreenDeviceAttributeDTO {

    @ApiModelProperty("通信编码")
    private String code;

    @ApiModelProperty("属性值")
    private String value;

}
