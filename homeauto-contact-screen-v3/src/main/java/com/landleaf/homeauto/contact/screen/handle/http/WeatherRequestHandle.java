package com.landleaf.homeauto.contact.screen.handle.http;

import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpCityWeatherDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpRequestDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpWeatherResponseDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.controller.inner.remote.AdapterClient;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.dto.payload.http.request.CityWeatherRequestPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.request.CommonHttpRequestPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.response.WeatherResponsePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 天气信息请求
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class WeatherRequestHandle extends AbstractHttpRequestHandler {


    @Autowired
    private AdapterClient adapterClient;

    public ContactScreenHttpResponse<WeatherResponsePayload> handlerRequest(CommonHttpRequestPayload requestPayload) {

        WeatherResponsePayload result = new WeatherResponsePayload();

        ContactScreenHeader header = ContactScreenContext.getContext();

        ScreenHttpRequestDTO requestDTO = new ScreenHttpRequestDTO();

        requestDTO.setScreenMac(header.getScreenMac());


        Response<ScreenHttpWeatherResponseDTO> responseDTO = null;
        try {
            responseDTO = adapterClient.getWeather(requestDTO);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (responseDTO != null && responseDTO.isSuccess()) {
            ScreenHttpWeatherResponseDTO tmpResult = responseDTO.getResult();

            BeanUtils.copyProperties(tmpResult, result);

            return returnSuccess(result);
        }
        return returnError(responseDTO);

    }
    public ContactScreenHttpResponse<WeatherResponsePayload> handlerRequest2(CityWeatherRequestPayload requestPayload) {

        WeatherResponsePayload result = new WeatherResponsePayload();

        ContactScreenHeader header = ContactScreenContext.getContext();

        ScreenHttpCityWeatherDTO requestDTO = new ScreenHttpCityWeatherDTO();

        requestDTO.setScreenMac(header.getScreenMac());
        requestDTO.setCity(requestPayload.getRequest());

        Response<ScreenHttpWeatherResponseDTO> responseDTO = null;
        try {
            responseDTO = adapterClient.getCityWeather(requestDTO);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (responseDTO != null && responseDTO.isSuccess()) {
            ScreenHttpWeatherResponseDTO tmpResult = responseDTO.getResult();

            BeanUtils.copyProperties(tmpResult, result);

            return returnSuccess(result);
        }
        return returnError(responseDTO);

    }
}