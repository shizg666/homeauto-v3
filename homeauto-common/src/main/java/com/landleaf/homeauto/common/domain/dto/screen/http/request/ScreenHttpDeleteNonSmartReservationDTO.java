package com.landleaf.homeauto.common.domain.dto.screen.http.request;

import lombok.Data;

/**
 * @ClassName ScreenDeleteNonSmartReservationDTO
 * @Description: 删除自由方舟预约请求
 * @Author wyl
 * @Date 2020/8/10
 * @Version V1.0
 **/
@Data
public class ScreenHttpDeleteNonSmartReservationDTO extends ScreenHttpRequestDTO {


    /**
     * 预约Id
     */
    private String revervationId;
}
