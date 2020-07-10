package com.landleaf.homeauto.common.domain.dto.oauth.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * APP账号登录DTO
 */
@Data
@ApiModel(value = "APP账号登录DTO")
public class CustomerLoginDto implements Serializable {

    private static final long serialVersionUID = 3636855934657884845L;

    @ApiModelProperty(value = "手机号", required = true)
    private String mobile;

    @ApiModelProperty(value = "密码", required = true)
    private String password;
}
