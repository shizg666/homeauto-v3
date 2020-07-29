package com.landleaf.homeauto.common.domain.dto.oauth.sysuser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统账号验证码相关业务校验
 *
 * @author pilo*/
@Data
@ApiModel(value = "SysUserAddReqDTO", description = "验证码校验DTO")
public class SysUserCheckCodeDTO {

    /**
     * 验证码类型
     */
    @ApiModelProperty(value = "1:邮箱；2：手机", required = true)
    private Integer type;

    @ApiModelProperty(value = "验证码",required = true)
    private String code;

    @ApiModelProperty(value = "账号",required = true)
    private String account;


}
