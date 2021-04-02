package com.landleaf.homeauto.common.domain.dto.oauth.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统账号验证码相关业务校验
 *
 * @author pilo*/
@Data
@ApiModel(value = "CustomerCheckCodeDTO", description = "验证码校验DTO")
public class CustomerCheckCodeDTO {


    @ApiModelProperty(value = "验证码",required = true)
    private String code;

    @ApiModelProperty(value = "账号",required = true)
    private String mobile;


}
