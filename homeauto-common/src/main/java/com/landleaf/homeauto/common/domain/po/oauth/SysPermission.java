package com.landleaf.homeauto.common.domain.po.oauth;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 后台账号操作权限表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "SysPermission对象", description = "后台账号操作权限表")
public class SysPermission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "前端标记")
    private String permissionKey;

    @ApiModelProperty(value = "权限名称")
    private String permissionName;

    @ApiModelProperty(value = "父级别菜单ID")
    private String pid;

    private String icon;

    @ApiModelProperty(value = "后端接口校验凭据")
    private String uri;

    @ApiModelProperty(value = "前端控制")
    private String url;

    @ApiModelProperty(value = "权限类型（菜单、按钮）")
    private Integer permissionType;

    @ApiModelProperty(value = "菜单排序")
    private Integer sort;
    @ApiModelProperty(value = "菜单component路径")
    private String componentPath;
    @ApiModelProperty(value = "meta")
    private String meta;


}
