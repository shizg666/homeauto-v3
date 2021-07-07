package com.landleaf.homeauto.common.domain.dto.oauth.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 客户数据传输DTO
 *
 * @author wenyilu*/
@Data
@ApiModel(value="CustomerAddReqDTO", description="app客户数据传输DTO")
public class CustomerAddReqDTO {

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "所属app(smart,non-smart)")
    private String belongApp;
    @ApiModelProperty(value = "性别 1：男，2：女，3：未知")
    private Integer sex;
    @ApiModelProperty(value = "证件")
    private String cert;
    @ApiModelProperty(value = "户籍")
    private String census;
    @ApiModelProperty(value = "证件类型 1:身份证 2：军官证")
    private Integer certType;
    @ApiModelProperty(value = "户籍路径")
    private String censusPath;
}
