package com.landleaf.homeauto.common.domain.dto.oauth.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 客户数据传输DTO
 *
 * @author wenyilu*/
@Data
@ApiModel(value = "CustomerUpdateReqDTO", description = "App客户修改数据传输DTO")
public class CustomerUpdateReqDTO {

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "主键")
    private String id;
    @ApiModelProperty(value = "所属app(smart,non-smart)")
    private String belongApp;
}
