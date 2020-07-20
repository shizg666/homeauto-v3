package com.landleaf.homeauto.common.domain.dto.oauth.sysuser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 个人资料（账号名称/手机号）修改 DTO
 *
 * @author wenyilu
 */
@Data
@ApiModel(value = "SysPersonalUpdateReqDTO", description = "个人资料（账号名称/手机号）修改")
public class SysPersonalUpdateReqDTO {

    @ApiModelProperty(value = "账号名称",required = true)
    private String name;

    @ApiModelProperty(value = "手机号",required = true)
    private String mobile;

    @ApiModelProperty(value = "验证码",required = true)
    private String code;

    @ApiModelProperty(value = "主键",required = true)
    private String userId;


}
