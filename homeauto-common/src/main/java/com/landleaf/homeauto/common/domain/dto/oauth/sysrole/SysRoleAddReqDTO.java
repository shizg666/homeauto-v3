package com.landleaf.homeauto.common.domain.dto.oauth.sysrole;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统角色新增请求DTO
 *
 * @author wenyilu
 */
@Data
@ApiModel(value = "系统角色新增请求DTO", description = "系统角色新增请求DTO")
public class SysRoleAddReqDTO {

    @ApiModelProperty(value = "角色名称",required = true)
    private String roleName;

    @ApiModelProperty(value = "角色类型1:朗绿,2:物业,3:其它",required = true)
    private Integer roleType;

    @ApiModelProperty(value = "启用/停用",required = true)
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;


}
