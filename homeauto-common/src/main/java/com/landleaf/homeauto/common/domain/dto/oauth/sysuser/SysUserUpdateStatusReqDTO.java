package com.landleaf.homeauto.common.domain.dto.oauth.sysuser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统账号启用停用请求DTO
 *
 * @author wenyilu
 */
@Data
@ApiModel(value = "SysUserUpdateStatusReqDTO", description = "系统账号启用停用请求DTO")
public class SysUserUpdateStatusReqDTO {

    @ApiModelProperty(value = "用户编码",required = true)
    private String userId;

    @ApiModelProperty(value = "状态",required = true)
    private Integer status;


}
