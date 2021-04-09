package com.landleaf.homeauto.contact.screen.handle.http;

import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpRequestDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpFamilyCodeResponseDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.controller.inner.remote.AdapterClient;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.dto.payload.http.request.CommonHttpRequestPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.response.FamilyCodeResponsePayload;
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
public class FamilyCodeRequestHandle extends AbstractHttpRequestHandler {


    @Autowired
    private AdapterClient adapterClient;

    public ContactScreenHttpResponse<FamilyCodeResponsePayload> handlerRequest(CommonHttpRequestPayload requestPayload) {

        FamilyCodeResponsePayload result = new FamilyCodeResponsePayload();

        ContactScreenHeader header = ContactScreenContext.getContext();

        ScreenHttpRequestDTO requestDTO = new ScreenHttpRequestDTO();

        requestDTO.setScreenMac(header.getScreenMac());

        Response<ScreenHttpFamilyCodeResponseDTO> response = null;
        try {
            response = adapterClient.getFamilyCode(requestDTO);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (response != null && response.isSuccess()) {

            ScreenHttpFamilyCodeResponseDTO tempResult = response.getResult();
            result.setFamilyCode(tempResult.getFamilyCode());

            return returnSuccess(response.getResult());
        }

        return returnError(response);


    }
}