package com.landleaf.homeauto.common.domain.po.oauth;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
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
    private Date bindTime;

    @ApiModelProperty(value = "绑定工程数")
    private Integer bindCount;

    @ApiModelProperty(value = "上次登录时间")
    private Date loginTime;


}
