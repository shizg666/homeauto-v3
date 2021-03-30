package com.landleaf.homeauto.common.domain.dto.oauth.sysuser;

import com.landleaf.homeauto.common.domain.qry.BaseQry;
import com.landleaf.homeauto.common.enums.oauth.UserTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 系统账号分页请求DTO
 *
 * @author wenyilu*/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "系统账号分页请求DTO", description = "系统账号分页请求DTO")
public class SysUserPageReqDTO extends BaseQry {

    @ApiModelProperty(value = "账号状态（1：启用；0：停用）")
    private Integer status;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "系统账号")
    private String email;

    @ApiModelProperty(value = "所属平台")
    private Integer plat;

    @ApiModelProperty(value = "创建时间范围")
    private List<String> createTimeFormat;


}
