package com.landleaf.homeauto.common.domain.dto.oauth.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 客户数据传输DTO
 *
 * @author wenyilu*/
@Data
@ApiModel(value = "ThirdCustomerBindFamilyReqDTO", description = "三方添加用户")
public class ThirdCustomerBindFamilyReqDTO {


    @ApiModelProperty(value = "手机号")
    private String mobile;


}
