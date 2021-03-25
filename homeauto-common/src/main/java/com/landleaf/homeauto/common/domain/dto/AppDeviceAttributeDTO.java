package com.landleaf.homeauto.common.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wenyilu
 * @ClassName 设备属性
 **/
@Data
public class AppDeviceAttributeDTO {

    @ApiModelProperty("通信编码")
    private String code;

    @ApiModelProperty("简码：通信编码去掉协议、区域、设备等标记，只保留属性如:switch")
    private String shortCode;

    @ApiModelProperty("属性值")
    private String value;

}
