package com.landleaf.homeauto.center.device.service.common;

import com.landleaf.homeauto.center.device.model.bo.WeatherBO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyWeatherVO;

/**
 * @author Yujiumin
 * @version 2020/10/16
 */
public interface FamilyWeatherService {

    /**
     * 通过weatherCode获取天气信息
     *
     * @param weatherCode 气象局给出的城市天气码
     * @return 天气信息
     */
    WeatherBO getWeatherByWeatherCode(String weatherCode);

    /**
     *  通过weatherCode获取APP家庭天气信息
     * @param weatherCode
     * @return com.landleaf.homeauto.center.device.model.smart.vo.FamilyWeatherVO
     * @author wenyilu
     * @date 2021/1/12 10:17
     */
    FamilyWeatherVO getWeatherByWeatherCode4VO(String weatherCode);
}
