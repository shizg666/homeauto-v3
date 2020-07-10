package com.landleaf.homeauto.common.domain.dto.oauth.sysuser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 后台账号登录DTO
 *
 * @author wenyilu
 */
@Data
@ApiModel(value = "后台账号登录DTO")
public class SysUserLoginDto implements Serializable {

    private static final long serialVersionUID = 281411354565065714L;

    @ApiModelProperty("邮箱/手机，使用邮箱/手机登录")
    private String account;

    @ApiModelProperty("密码")
    private String password;
}
