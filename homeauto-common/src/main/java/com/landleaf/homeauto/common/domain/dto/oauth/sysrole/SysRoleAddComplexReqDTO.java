package com.landleaf.homeauto.common.domain.dto.oauth.sysrole;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 系统角色新增请求复合DTO
 *
 * @author wenyilu
 */
@Data
@ApiModel(value = "系统角色新增请求复合DTO", description = "系统角色新增请求复合DTO")
public class SysRoleAddComplexReqDTO {

    @ApiModelProperty(value = "角色信息")
    private SysRoleAddReqDTO sysRole;

    @ApiModelProperty(value = "权限ID集合")
    private List<String> sysPermissionIds;

    @ApiModelProperty(value = "范围path集合")
    private List<String> scopPaths;


}
