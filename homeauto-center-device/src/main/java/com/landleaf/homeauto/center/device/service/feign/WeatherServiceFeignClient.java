package com.landleaf.homeauto.center.device.service.feign;

import com.landleaf.homeauto.center.device.model.bo.WeatherBO;
import com.landleaf.homeauto.common.constant.ServerNameConst;
import com.landleaf.homeauto.common.domain.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 天气服务
 *
 * @author Yujiumin
 * @version 2020/8/21
 */
@FeignClient(ServerNameConst.HOMEAUTO_CENTER_WEATHER)
@RequestMapping("weather")
public interface WeatherServiceFeignClient {

    /**
     * 通过城市code获取天气信息
     *
     * @param weatherCode 城市编码
     * @return 天气信息
     */
    @GetMapping("code")
    Response<WeatherBO> getWeatherByWeatherCode(@RequestParam("city") String weatherCode);

}
