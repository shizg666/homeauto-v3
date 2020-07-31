package com.landleaf.homeauto.common.domain.dto.oauth.app;

import lombok.Data;

/**
 * @ClassName AppLoginRequestDTO
 * @Description: app登录请求DTO
 * @Author wyl
 * @Date 2020/7/31
 * @Version V1.0
 **/
@Data
public class AppLoginRequestDTO {

    private String mobile;
    private String password;
    private String clientId;
    private String clientSecret;
}
