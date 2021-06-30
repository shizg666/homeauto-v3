package com.landleaf.homeauto.center.weather.controller;

import com.landleaf.homeauto.center.weather.model.bo.WeatherBO;
import com.landleaf.homeauto.center.weather.model.vo.WeatherForAppVO;
import com.landleaf.homeauto.center.weather.service.WeatherService;

import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author Yujiumin
 * @version 2020/8/18
 */
@RestController
@RequestMapping
@Api(value = "天气接口", tags = "天气接口")
public class WeatherController extends BaseController {

    private WeatherService weatherService;

    @Autowired
    public void setWeatherService(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("name")
    @ApiOperation("通过城市名称获取天气信息")
    public Response<WeatherForAppVO> getWeatherByName(@RequestParam String city) {
        checkNull(city);
        WeatherForAppVO weatherForAppVO = weatherService.getWeatherByCityNameForApp(city);
        return returnSuccess(weatherForAppVO);
    }
    @GetMapping("/screen/name")
    @ApiOperation("大屏通过城市名直接调用天气接口")
    public Response<WeatherBO> getWeatherByName4Screen(@RequestParam("cityName") String cityName) {
        checkNull(cityName);
        WeatherBO weatherBO = weatherService.getWeatherByCityName(cityName);
        return returnSuccess(weatherBO);
    }

    @GetMapping("code")
    @ApiOperation("通过城市编码获取信息")
    public Response<WeatherBO> getWeatherByCode(@RequestParam String city) {
        checkNull(city);
        WeatherBO weatherBO = weatherService.getWeatherBoByCityCode(city);
        return returnSuccess(weatherBO);
    }

    /**
     * 检查参数是否为空
     *
     * @param object 参数
     */
    private void checkNull(String object) {
        if (StringUtils.isEmpty(object)) {
            throw new NullPointerException("参数不可为空");
        }
    }

}
