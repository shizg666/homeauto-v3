package com.landleaf.homeauto.common.domain.dto.oauth.sysrole;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 后台账号角色
 * </p>
 *
 * @author wyl
 */
@Data
@ApiModel(value = "SysRoleDTO", description = "SysRoleDTO")
public class SysRoleDTO implements Serializable {

    private static final long serialVersionUID = -7462091749321521569L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色类型")
    private Integer roleType;

    @ApiModelProperty(value = "角色类型名称")
    private String roleTypeName;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "启用标识，0：禁用，1：启用")
    private Integer status;

    @ApiModelProperty(value = "启用标识")
    private String statusName;


}
