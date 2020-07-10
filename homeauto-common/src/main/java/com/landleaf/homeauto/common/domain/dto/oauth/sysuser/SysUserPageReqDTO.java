package com.landleaf.homeauto.common.domain.dto.oauth.sysuser;

import com.landleaf.homeauto.common.domain.qry.BaseQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统账号分页请求DTO
 *
 * @author wenyilu*/
@Data
@ApiModel(value = "系统账号分页请求DTO", description = "系统账号分页请求DTO")
public class SysUserPageReqDTO extends BaseQry {

    @ApiModelProperty(value = "账号状态（1：启用；0：停用）")
    private Integer status;

    @ApiModelProperty(value = "角色ID")
    private String roleId;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "姓名")
    private String name;


}
