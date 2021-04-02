package com.landleaf.homeauto.common.domain.dto.oauth.wechat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName AppLoginRequestDTO
 * @Description: WechatLoginRequestDTO
 * @Author wyl
 * @Date 2020/7/31
 * @Version V1.0
 **/
@Data
public class WechatBindPhoneRequestDTO {

    private String openId;
    private String bindAuthroizeCode;
    private String encrypteData;
    private String iv;
    private String phone;
    @ApiModelProperty(value = "头像地址(http://)")
    private String avatar;
    private String name;
}
