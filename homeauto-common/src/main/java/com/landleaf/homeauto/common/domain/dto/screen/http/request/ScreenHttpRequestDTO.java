package com.landleaf.homeauto.common.domain.dto.screen.http.request;

import lombok.Data;

/**
 * @ClassName ScreenBaseDTO
 * @Description: 大屏http请求数据DTO
 * @Author wyl
 * @Date 2020/8/10
 * @Version V1.0
 **/
@Data
public class ScreenHttpRequestDTO {

    /**
     * 家庭编码
     */
    private String familyCode;
    /**
     * 大屏mac
     */
    private String screenMac;
    /**
     * 家庭方案
     */
    private String familyScheme;


}
