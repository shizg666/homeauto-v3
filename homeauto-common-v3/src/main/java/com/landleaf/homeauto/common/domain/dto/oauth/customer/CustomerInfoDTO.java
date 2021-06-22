package com.landleaf.homeauto.common.domain.dto.oauth.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * <p>
 * 客户详情DTO
 * </p>
 *
 * @author wyl
 */
@Data
@ApiModel(value = "CustomerInfoDTO", description = "客户详情DTO")
public class CustomerInfoDTO {
    private String id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "头像地址(http://)")
    private String avatar;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "是否绑定工程")
    private Integer bindFlag;

    @ApiModelProperty(value = "是否绑定工程")
    private String bindFlagName;

    @ApiModelProperty(value = "首次绑定时间")
    private Date bindTime;
    
    @ApiModelProperty(value = "首次绑定时间")
    private String bindTimeFormat;

    @ApiModelProperty(value = "绑定工程数")
    private Integer bindCount;

    @ApiModelProperty(value = "上次登录时间")
    private Date loginTime;

    @ApiModelProperty(value = "上次登录时间")
    private String loginTimeFormat;
    @ApiModelProperty(value = "所属app")
    private String belongApp;
    @ApiModelProperty(value = "性别 1：男，2：女，3：未知")
    private Integer sex;
    @ApiModelProperty(value = "证件类型 1:身份证 2：军官证")
    private Integer certType;
    @ApiModelProperty(value = "证件")
    private String cert;
    @ApiModelProperty(value = "户籍路径")
    private String censusPath;
    @ApiModelProperty(value = "户籍")
    private String censusName;




}
