package com.landleaf.homeauto.contact.screen.handle.http;

import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpFamilyBindDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.controller.inner.remote.AdapterClient;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.dto.payload.http.request.FamilyBindRequestPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.response.NewsResponsePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 大屏绑定payload
 *
 * @author zhanghongbin
 */
@Component
@Slf4j
public class FamilyBindRequestHandle extends AbstractHttpRequestHandler {

    @Autowired
    private AdapterClient adapterClient;

    public ContactScreenHttpResponse<List<NewsResponsePayload>> handlerRequest(FamilyBindRequestPayload requestPayload) {


        ScreenHttpFamilyBindDTO requestDTO = new ScreenHttpFamilyBindDTO();
        BeanUtils.copyProperties(requestPayload.getRequest(),requestDTO);

        ContactScreenHeader header = ContactScreenContext.getContext();
        requestDTO.setScreenMac(header.getScreenMac());

        Response responseDTO = null;
        try {
            responseDTO = adapterClient.familyBind(requestDTO);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (responseDTO != null && responseDTO.isSuccess()) {

            return returnSuccess();
        }

        return returnError(responseDTO);
    }


}