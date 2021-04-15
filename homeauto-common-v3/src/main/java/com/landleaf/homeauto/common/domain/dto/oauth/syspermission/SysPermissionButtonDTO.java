package com.landleaf.homeauto.common.domain.dto.oauth.syspermission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Dto - 按钮权限DTO
 * @author wenyilu
 */
@Data
@ApiModel(value = "SysPermissionButtonDTO", description = "按钮权限DTO")
public class SysPermissionButtonDTO {

    @ApiModelProperty(value = "权限名称")
    private String permissionName;
    @ApiModelProperty(value = "权限code")
    private String permissionCode;

    @ApiModelProperty(value = "父级别菜单ID")
    private String pid;

    @ApiModelProperty(value = "后端接口校验凭据")
    private String path;

    @ApiModelProperty(value = "前端组件名")
    private String componentName;

    @ApiModelProperty(value = "重定向地址")
    private String redirect;

    @ApiModelProperty(value = "权限类型（菜单、按钮）")
    private Integer permissionType;

    @ApiModelProperty(value = "菜单排序")
    private Integer sort;

    @ApiModelProperty(value = "meta")
    private String meta;

}
