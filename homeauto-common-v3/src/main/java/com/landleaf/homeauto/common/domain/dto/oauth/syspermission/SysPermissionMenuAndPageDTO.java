package com.landleaf.homeauto.common.domain.dto.oauth.syspermission;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * Dto - 菜单+页面权限DTO
 *
 * @author wenyilu
 */
@Data
@ApiModel(value = "SysPermissionMenuAndPageDTO", description = "菜单+页面权限DTO")
public class SysPermissionMenuAndPageDTO {

    private List<SysPermissionMenuDTO> menus;

    private List<SysPermissionPageDTO> pages;

}
