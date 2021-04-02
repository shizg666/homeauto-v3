package com.landleaf.homeauto.common.domain.dto.oauth.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * APP用户注册DTO
 * @author wenyilu
 */
@ApiModel("APP用户注册DTO")
@Data
public class CustomerRegisterDTO implements Serializable {

    private static final long serialVersionUID = 5353675331507768493L;

    @ApiModelProperty(value = "手机号",required = true)
    private String mobile;

    @ApiModelProperty(value = "验证码",required = true)
    private String code;

    @ApiModelProperty(value = "密码",required = true)
    private String password;

}
