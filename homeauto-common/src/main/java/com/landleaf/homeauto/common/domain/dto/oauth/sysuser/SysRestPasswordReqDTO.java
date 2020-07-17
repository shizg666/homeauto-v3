package com.landleaf.homeauto.common.domain.dto.oauth.sysuser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 个人资料重置密码 DTO
 *
 * @author wenyilu*/
@Data
@ApiModel(value = "SysRestPasswordReqDTO", description = "个人资料重置密码")
public class SysRestPasswordReqDTO {

    @ApiModelProperty(value = "主键",required = true)
    private String id;

    @ApiModelProperty(value = "旧密码（MD5加密一次）",required = true)
    private String oldPassword;

    @ApiModelProperty(value = "新密码(md5加密一次)",required = true)
    private String newPassword;


}
