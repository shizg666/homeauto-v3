package com.landleaf.homeauto.common.domain.dto.screen.http.response;

import lombok.Data;

/**
 * 节假日判定请求返回
 * @author wenyilu
 */
@Data
public class ScreenHttpHolidaysCheckResponseDTO{

    /**
     * 是否节假日
     */
    private Boolean result;

}
