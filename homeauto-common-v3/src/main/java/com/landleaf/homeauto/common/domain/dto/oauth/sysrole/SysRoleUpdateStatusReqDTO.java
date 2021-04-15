package com.landleaf.homeauto.common.domain.dto.oauth.sysrole;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统账号角色启用停用请求DTO
 *
 * @author wenyilu
 */
@Data
@ApiModel(value = "系统账号角色启用停用请求DTO", description = "系统账号角色启用停用请求DTO")
public class SysRoleUpdateStatusReqDTO {

    @ApiModelProperty(value = "角色编码",required = true)
    private String roleId;

    @ApiModelProperty(value = "状态",required = true)
    private Integer status;


}
