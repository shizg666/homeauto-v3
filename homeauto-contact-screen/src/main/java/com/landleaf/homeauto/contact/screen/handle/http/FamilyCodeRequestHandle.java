package com.landleaf.homeauto.contact.screen.handle.http;

import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpRequestDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpFamilyCodeResponseDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.dto.payload.http.request.CommonHttpRequestPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.response.FamilyCodeRequestReplyPayload;
import com.landleaf.homeauto.contact.screen.controller.inner.remote.AdapterClient;
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

    public ContactScreenHttpResponse<FamilyCodeRequestReplyPayload> handlerRequest(CommonHttpRequestPayload requestPayload) {

        FamilyCodeRequestReplyPayload result = new FamilyCodeRequestReplyPayload();

        ContactScreenHeader header = ContactScreenContext.getContext();

        ScreenHttpRequestDTO requestDTO = new ScreenHttpRequestDTO();

        requestDTO.setScreenMac(header.getScreenMac());

        Response<ScreenHttpFamilyCodeResponseDTO> responseDTO = adapterClient.getFamilyCode(requestDTO);

        ScreenHttpFamilyCodeResponseDTO tempResult = responseDTO.getResult();
        result.setFamilyCode(tempResult.getFamilyCode());

        return returnSuccess(result);

    }
}