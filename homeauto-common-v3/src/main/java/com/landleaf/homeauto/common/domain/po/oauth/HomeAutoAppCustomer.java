package com.landleaf.homeauto.common.domain.po.oauth;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * <p>
 * APP客户列表
 * </p>
 *
 * @author wyl
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "HomeAutoAppCustomer", description = "APP客户列表")
public class HomeAutoAppCustomer extends BaseEntity {

    private static final long serialVersionUID = 1L;

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

    @ApiModelProperty(value = "首次绑定时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="CTT")
    private Date bindTime;

    @ApiModelProperty(value = "绑定工程数")
    private Integer bindCount;

    @ApiModelProperty(value = "上次登录时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="CTT")
    private Date loginTime;

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
