package com.landleaf.homeauto.contact.screen.handle.http;

import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpHolidaysCheckDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpHolidaysCheckResponseDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.controller.inner.remote.AdapterClient;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.dto.payload.http.request.HolidaysCheckRequestPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.response.HolidaysCheckRequestReplyPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 获取家庭码
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class HolidaysCheckRequestHandle extends AbstractHttpRequestHandler {

    @Autowired
    private AdapterClient adapterClient;

    public ContactScreenHttpResponse<HolidaysCheckRequestReplyPayload> handlerRequest(HolidaysCheckRequestPayload requestPayload) {

        HolidaysCheckRequestReplyPayload result = new HolidaysCheckRequestReplyPayload();

        ContactScreenHeader header = ContactScreenContext.getContext();

        ScreenHttpHolidaysCheckDTO requestDTO = new ScreenHttpHolidaysCheckDTO();

        requestDTO.setDate(requestPayload.getRequest());
        requestDTO.setScreenMac(header.getScreenMac());

        Response<ScreenHttpHolidaysCheckResponseDTO> responseDTO = null;
        try {
            responseDTO = adapterClient.holidayCheck(requestDTO);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (responseDTO != null && responseDTO.isSuccess()) {
            result.setResult(responseDTO.getResult().getResult());
            return returnSuccess(result);
        }


        return returnError(responseDTO);

    }
}