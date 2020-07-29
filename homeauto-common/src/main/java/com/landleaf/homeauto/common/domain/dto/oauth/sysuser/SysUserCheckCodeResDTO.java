package com.landleaf.homeauto.common.domain.dto.oauth.sysuser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 验证码校验返回DTO
 *
 * @author pilo*/
@Data
@ApiModel(value = "SysUserCheckCodeResDTO", description = "验证码校验返回DTO")
public class SysUserCheckCodeResDTO {

    @ApiModelProperty(value = "校验结果", required = true)
    private Boolean result;


}
