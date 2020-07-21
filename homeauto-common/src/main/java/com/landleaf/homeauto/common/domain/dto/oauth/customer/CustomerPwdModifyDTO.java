package com.landleaf.homeauto.common.domain.dto.oauth.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 密码修改DTO
 * </p>
 *
 * @author wenyilu
 */
@Data
@ApiModel(value = "CustomerPwdModifyDTO")
public class CustomerPwdModifyDTO {

    @ApiModelProperty(value = "新密码",required = true)
    private String newPwd;

    @ApiModelProperty(value = "确认密码",required = true)
    private String confirmPwd;
}
