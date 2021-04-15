package com.landleaf.homeauto.common.domain.vo.oauth;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * vo - 页面权限VO
 *
 * @author wenyilu
 */
@Data
@ApiModel(value = "SysPermissionPageVO", description = "按钮权限DTO")
public class SysPermissionPageVO {

    @ApiModelProperty(value = "权限名称")
    private String permissionName;

    @ApiModelProperty(value = "权限code")
    private String permissionCode;

    /**
     * 用户按钮权限信息
     */
    private List<SysPermissionButtonVO> actions = Lists.newArrayList();

}
