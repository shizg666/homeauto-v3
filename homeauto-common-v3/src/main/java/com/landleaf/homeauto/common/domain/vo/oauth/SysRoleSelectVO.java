package com.landleaf.homeauto.common.domain.vo.oauth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 后台账号角色下拉列表
 * </p>
 *
 * @author wyl
 */
@Data
@ApiModel(value="SysRoleSelectVO", description="SysRoleSelectVO")
public class SysRoleSelectVO implements Serializable {

    private static final long serialVersionUID = -7462091749321521569L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "角色名称")
    private String roleName;



}
