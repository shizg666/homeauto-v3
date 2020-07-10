package com.landleaf.homeauto.common.domain.dto.oauth.sysrole;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统角色修改请求DTO
 *
 * @author wenyilu
 */
@Data
@ApiModel(value = "系统角色修改请求DTO", description = "系统角色修改请求DTO")
public class SysRoleUpdateReqDTO {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色类型1:朗绿,2:物业,3:其它")
    private Integer roleType;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "启用标识，0：禁用，1：启用")
    private Integer status;


}
