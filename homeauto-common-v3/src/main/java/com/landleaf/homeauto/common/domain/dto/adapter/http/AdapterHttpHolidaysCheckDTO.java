package com.landleaf.homeauto.common.domain.dto.adapter.http;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageHttpDTO;
import lombok.Data;

/**
 * 节假日判断请求
 *
 * @author wenyilu
 */
@Data
public class AdapterHttpHolidaysCheckDTO extends AdapterMessageHttpDTO {

    /**
     * 判断某一天是否是节假日格式:yyyy-MM-dd
     */
    private String date;


}
