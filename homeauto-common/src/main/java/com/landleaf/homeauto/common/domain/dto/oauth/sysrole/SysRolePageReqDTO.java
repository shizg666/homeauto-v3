package com.landleaf.homeauto.common.domain.dto.oauth.sysrole;

import com.landleaf.homeauto.common.domain.qry.BaseQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 系统账号角色列表分页请求DTO
 *
 * @author wenyilu
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "系统账号角色列表分页请求DTO", description = "系统账号角色列表分页请求DTO")
public class SysRolePageReqDTO extends BaseQry {

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "启用标识，0：禁用，1：启用")
    private Integer status;
    @ApiModelProperty(value = "启用标识，0：禁用，1：启用")
    private Integer statusName;
    @ApiModelProperty(value = "创建时间范围")
    private List<String> createTime;

}
