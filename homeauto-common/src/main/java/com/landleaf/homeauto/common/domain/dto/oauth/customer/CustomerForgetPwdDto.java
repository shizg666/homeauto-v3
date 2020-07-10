package com.landleaf.homeauto.common.domain.dto.oauth.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 忘记密码DTO
 * @author wenyilu
 */
@ApiModel(value = "忘记密码DTO")
@Data
public class CustomerForgetPwdDto implements Serializable {

    private static final long serialVersionUID = 2533124435813205759L;

    @ApiModelProperty(value = "手机号",required = true)
    private String mobile;

    @ApiModelProperty(value = "验证码",required = true)
    private String code;

    @ApiModelProperty(value = "密码",required = true)
    private String password;

}
