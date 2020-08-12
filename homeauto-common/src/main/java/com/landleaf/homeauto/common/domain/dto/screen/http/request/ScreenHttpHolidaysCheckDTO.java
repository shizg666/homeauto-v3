package com.landleaf.homeauto.common.domain.dto.screen.http.request;

import lombok.Data;

/**
 * 节假日判断请求
 *
 * @author wenyilu
 */
@Data
public class ScreenHttpHolidaysCheckDTO extends ScreenHttpRequestDTO {

    /**
     * 判断某一天是否是节假日格式:yyyy-MM-dd
     */
    private String date;


}
