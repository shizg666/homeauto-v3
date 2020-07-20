package com.landleaf.homeauto.common.domain.dto.oauth.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wenyilu
 */
@Data
@ApiModel("客户微信方式登录返回对象")
public class CustomerWechatLoginResDTO {

    @ApiModelProperty("客户ID")
    private String userId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "头像地址(http://)")
    private String avatar;

    @ApiModelProperty(value = "openId")
    private String openId;

    @ApiModelProperty(value = "该用户是否绑定了手机号")
    private Boolean havePhone;

    @ApiModelProperty(value = "该用户是否注册")
    private Boolean haveUser;

    @ApiModelProperty(value = "游客模式")
    private Boolean touristMode;

    @ApiModelProperty(value = "token")
    private String accessToken;

    public CustomerWechatLoginResDTO() {
    }

    public CustomerWechatLoginResDTO(String userId, String name, String mobile, String avatar, String openId, Boolean havePhone, Boolean haveUser, Boolean touristMode, String accessToken) {
        this.userId = userId;
        this.name = name;
        this.mobile = mobile;
        this.avatar = avatar;
        this.openId = openId;
        this.havePhone = havePhone;
        this.haveUser = haveUser;
        this.touristMode = touristMode;
        this.accessToken = accessToken;
    }
}
