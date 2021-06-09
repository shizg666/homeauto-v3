package com.landleaf.homeauto.center.device.remote;

import com.landleaf.homeauto.center.device.model.bo.WeatherBO;
import com.landleaf.homeauto.common.constant.ServerNameConst;
import com.landleaf.homeauto.common.domain.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wenyilu
 */
@FeignClient(name = ServerNameConst.HOMEAUTO_CENTER_WEATHER)
public interface WeatherRemote {


    @GetMapping("/weather/code")
    @ApiOperation("通过天气城市编码获取信息")
    Response<WeatherBO> getWeatherByCode(@RequestParam("city") String city);

    @GetMapping("/weather/screen/name")
    @ApiOperation("大屏通过城市名直接调用天气接口")
    public Response<WeatherBO> getWeatherByName4Screen(@RequestParam String cityName);
}
