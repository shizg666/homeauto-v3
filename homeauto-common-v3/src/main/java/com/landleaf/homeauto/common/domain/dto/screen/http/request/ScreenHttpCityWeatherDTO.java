package com.landleaf.homeauto.common.domain.dto.screen.http.request;

import lombok.Data;

/**
 * 根据城市获取天气
 *
 * @author wenyilu
 */
@Data
public class ScreenHttpCityWeatherDTO extends ScreenHttpRequestDTO {

    /**
     * 根据城市获取天气
     */
    private String city;


}
