package com.landleaf.homeauto.common.domain.dto.oauth.wechat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 微信小程序获取本系统token请求体
 *
 * @author pilo*/
@Data
@ApiModel(value = "WechatTokenRequestDTO", description = "微信小程序获取本系统token请求体")
public class WechatTokenRequestDTO {


    @ApiModelProperty(value = "登录验证时发放code",required = true)
    private String bindAuthroizeCode;

    @ApiModelProperty(value = "手机号",required = true)
    private String mobile;


}
