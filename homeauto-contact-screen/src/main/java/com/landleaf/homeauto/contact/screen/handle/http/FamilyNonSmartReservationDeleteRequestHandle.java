package com.landleaf.homeauto.contact.screen.handle.http;

import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpDeleteNonSmartReservationDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.dto.payload.http.request.FamilyNonSmartReservationDeleteRequestPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.response.FamilyNonSmartReservationDeleteRequestReplyPayload;
import com.landleaf.homeauto.contact.screen.controller.inner.remote.AdapterClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 预约（自由方舟）删除请求
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class FamilyNonSmartReservationDeleteRequestHandle extends AbstractHttpRequestHandler {

    @Autowired
    private AdapterClient adapterClient;

    public ContactScreenHttpResponse<FamilyNonSmartReservationDeleteRequestReplyPayload> handlerRequest(FamilyNonSmartReservationDeleteRequestPayload requestPayload) {

        ContactScreenHeader header = ContactScreenContext.getContext();

        ScreenHttpDeleteNonSmartReservationDTO requestDTO = new ScreenHttpDeleteNonSmartReservationDTO();

        requestDTO.setScreenMac(header.getScreenMac());

        requestDTO.setRevervationId(requestPayload.getReservationId());

        Response response = adapterClient.deleteNonSmartReservation(requestDTO);

        return returnSuccess();

    }

}