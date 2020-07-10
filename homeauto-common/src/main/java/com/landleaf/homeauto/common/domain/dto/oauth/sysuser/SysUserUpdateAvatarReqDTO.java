package com.landleaf.homeauto.common.domain.dto.oauth.sysuser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统账号修改头像请求 DTO
 *
 * @author wenyilu*/
@Data
@ApiModel(value = "SysUserUpdateAvatarReqDTO", description = "系统账号修改头像请求")
public class SysUserUpdateAvatarReqDTO {

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "主键")
    private String id;


}
