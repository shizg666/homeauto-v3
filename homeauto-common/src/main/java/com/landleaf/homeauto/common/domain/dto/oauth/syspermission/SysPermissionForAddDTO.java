package com.landleaf.homeauto.common.domain.dto.oauth.syspermission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Dto - 添加权限
 * @author wenyilu
 */
@Data
@ApiModel(value = "SysPermissionForAddDTO对象", description = "新增权限dto")
public class SysPermissionForAddDTO {

    @ApiModelProperty(value = "权限名称",required = true)
    private String permissionName;
    @ApiModelProperty(value = "权限code")
    private String permissionCode;

    @ApiModelProperty(value = "父级别菜单ID",required = true)
    private String pid;

    @ApiModelProperty(value = "后端接口校验凭据",required = true)
    private String path;

    @ApiModelProperty(value = "前端组件名",required = true)
    private String componentName;

    @ApiModelProperty(value = "重定向地址")
    private String redirect;

    @ApiModelProperty(value = "权限类型（菜单、按钮）",required = true)
    private Integer permissionType;

    @ApiModelProperty(value = "菜单排序",required = true)
    private Integer sort;

    @ApiModelProperty(value = "meta")
    private String meta;

}
