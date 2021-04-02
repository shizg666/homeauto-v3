package com.landleaf.homeauto.common.domain.dto.screen.http.request;

import lombok.Data;

@Data
public class ScreenHttpFamilyBindDTO extends ScreenHttpRequestDTO {


    /**
     * 约定的家庭编号
     * "familyCode": "a10101-01010101",
     */
    private String familyCode;

}
