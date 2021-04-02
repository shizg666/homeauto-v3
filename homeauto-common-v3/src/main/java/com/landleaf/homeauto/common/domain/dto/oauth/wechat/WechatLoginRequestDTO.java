package com.landleaf.homeauto.common.domain.dto.oauth.wechat;

import lombok.Data;

/**
 * @ClassName AppLoginRequestDTO
 * @Description: WechatLoginRequestDTO
 * @Author wyl
 * @Date 2020/7/31
 * @Version V1.0
 **/
@Data
public class WechatLoginRequestDTO {

    private String code;
    private String clientId;
    private String clientSecret;
}
