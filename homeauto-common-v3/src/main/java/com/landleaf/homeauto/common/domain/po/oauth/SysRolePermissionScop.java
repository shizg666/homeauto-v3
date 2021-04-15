package com.landleaf.homeauto.common.domain.po.oauth;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 后台账号角色权限范围表
 * </p>
 *@author wyl
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "SysRolePermissionScop对象", description = "后台账号角色权限范围表")
public class SysRolePermissionScop extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    private String roleId;

    @ApiModelProperty(value = "地址路径如武汉市（/中国编码/湖北省编码/武汉市编码）")
    private String path;


}
