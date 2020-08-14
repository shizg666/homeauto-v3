package com.landleaf.homeauto.common.domain.dto.device.sobot.token;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * token请求返回对象
 * @author wenyilu
 */
@Data
public class SobotTokenResponseItemDTO {


    @ApiModelProperty(value = "token编码\t", name = "token", required = true, dataType = "String")
    private String token;

    @ApiModelProperty(value = "凭证有效时间 单位：秒", name = "expires_in", required = true, dataType = "String")
    private String expires_in;
}
