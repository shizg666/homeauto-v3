package com.landleaf.homeauto.common.domain.dto.screen.http.request;

import lombok.Data;

import java.util.List;

/**
 * @ClassName ScreenDeleteNonSmartReservationDTO
 * @Description: 删除定时场景配置请求
 * @Author wyl
 * @Date 2020/8/10
 * @Version V1.0
 **/
@Data
public class ScreenHttpDeleteTimingSceneDTO extends ScreenHttpRequestDTO {


    /**
     * 定时配置Id
     */
    private List<String> ids;
}
