package com.landleaf.homeauto.contact.screen.handle.http;

import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpDeleteNonSmartSceneDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.dto.payload.http.request.FamilyNonSmartSceneDeleteRequestPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.response.FamilyNonSmartSceneDeleteRequestReplyPayload;
import com.landleaf.homeauto.contact.screen.controller.inner.remote.AdapterClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 场景（自由方舟）删除请求
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class FamilyNonSmartSceneDeleteRequestHandle extends AbstractHttpRequestHandler {


    @Autowired
    private AdapterClient adapterClient;

    public ContactScreenHttpResponse<FamilyNonSmartSceneDeleteRequestReplyPayload> handlerRequest(FamilyNonSmartSceneDeleteRequestPayload requestPayload) {


        ContactScreenHeader header = ContactScreenContext.getContext();

        ScreenHttpDeleteNonSmartSceneDTO requestDTO = new ScreenHttpDeleteNonSmartSceneDTO();

        requestDTO.setFamilyCode(header.getFamilyCode());

        requestDTO.setScreenMac(header.getScreenMac());

        requestDTO.setSceneId(requestPayload.getSceneId());

        Response response = adapterClient.deleteNonSmartScene(requestDTO);

        return returnSuccess();

    }


}