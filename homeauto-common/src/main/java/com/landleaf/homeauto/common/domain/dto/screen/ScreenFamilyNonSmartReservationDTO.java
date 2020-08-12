package com.landleaf.homeauto.common.domain.dto.screen;

import lombok.Data;

/**
 * 自由方舟预约新增修改请求响应payload
 *
 * @author wenyilu
 */
@Data
public class ScreenFamilyNonSmartReservationDTO {


    /**
     * 主键，若有为修改，否则为新增，响应返回填充id
     */
    private String reservationId;

}
