package com.landleaf.homeauto.contact.screen.handle.http;

import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpRequestDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpHolidaysCheckResponseDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpWeatherResponseDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.controller.inner.remote.AdapterClient;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.dto.payload.http.request.CommonHttpRequestPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.response.WeatherRequestReplyPayload;
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

    public ContactScreenHttpResponse<WeatherRequestReplyPayload> handlerRequest(CommonHttpRequestPayload requestPayload) {

        WeatherRequestReplyPayload result = new WeatherRequestReplyPayload();

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


        return returnError();

    }
}