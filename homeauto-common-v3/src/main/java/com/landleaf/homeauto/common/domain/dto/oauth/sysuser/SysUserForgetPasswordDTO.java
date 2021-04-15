package com.landleaf.homeauto.common.domain.dto.oauth.sysuser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 忘记密码dto
 *
 * @author wenyilu
 */
@Data
@ApiModel("系统账号忘记密码DTO")
public class SysUserForgetPasswordDTO implements Serializable {
    private static final long serialVersionUID = -6682949981325952200L;
    /**
     * 找回密码方式
     */
    @ApiModelProperty(value = "找回密码方式1:邮箱；2：手机", required = true)
    private Integer type;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "email",required = true)
    private String email;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号",required = true)
    private String mobile;
    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码",required = true)
    private String code;
    /**
     * 新密码
     */
    @ApiModelProperty(value = "新密码",required = true)
    private String newPassword;
}
