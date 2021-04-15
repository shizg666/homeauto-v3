package com.landleaf.homeauto.common.domain.vo.oauth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Dto - 按钮权限VO
 *
 * @author wenyilu
 */
@Data
@ApiModel(value = "SysPermissionButtonVO", description = "按钮权限VO")
public class SysPermissionButtonVO {

    @ApiModelProperty(value = "权限code")
    private String action;
    @ApiModelProperty(value = "权限名称")
    private String describe;

}
