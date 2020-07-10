package com.landleaf.homeauto.common.domain.dto.oauth.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wenyilu
 */
@Data
@ApiModel("客户注册返回DTO")
public class CustomerRegisterResDTO {

    @ApiModelProperty("客户ID")
    private String userId;

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "头像地址(http://)")
    private String avatar;


}
