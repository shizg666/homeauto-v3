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

    @ApiModelProperty(value = "openId")
    private String openId;


}
