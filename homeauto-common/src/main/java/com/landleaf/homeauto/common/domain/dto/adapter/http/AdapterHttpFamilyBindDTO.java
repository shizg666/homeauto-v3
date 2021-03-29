package com.landleaf.homeauto.common.domain.dto.adapter.http;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageHttpDTO;
import lombok.Data;

/**
 * 大屏apk版本检查
 * @author wenyilu
 */
@Data
public class AdapterHttpFamilyBindDTO extends AdapterMessageHttpDTO {


    /**
     * 约定的家庭编号
     * "familyCode": "a10101-01010101",
     */
    private String familyCode;

}
