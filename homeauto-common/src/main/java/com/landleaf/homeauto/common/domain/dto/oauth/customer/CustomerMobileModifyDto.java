package com.landleaf.homeauto.common.domain.dto.oauth.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 修改手机号DTO
 * @author wenyilu
 */
@ApiModel(value = "修改手机号DTO")
@Data
public class CustomerMobileModifyDto implements Serializable {

    private static final long serialVersionUID = 2533124435813205759L;

    @ApiModelProperty(value = "修改后手机号",required = true)
    private String mobile;

    @ApiModelProperty(value = "验证码",required = false)
    private String code;


}
