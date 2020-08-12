package com.landleaf.homeauto.contact.screen.handle.http;

import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpApkUpdateResultDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.dto.payload.http.request.ApkUpdateResultRequestPayload;
import com.landleaf.homeauto.contact.screen.controller.inner.remote.AdapterClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 大屏apk升级结回调通知
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class ApkUpdateResultRequestHandle extends AbstractHttpRequestHandler {

    @Autowired
    private AdapterClient adapterClient;

    public ContactScreenHttpResponse handlerRequest(ApkUpdateResultRequestPayload requestPayload) {

        ContactScreenHeader header = ContactScreenContext.getContext();

        ScreenHttpApkUpdateResultDTO requestDTO = new ScreenHttpApkUpdateResultDTO();

        requestDTO.setVersion(requestPayload.getVersion());
        requestDTO.setFamilyCode(header.getFamilyCode());
        requestDTO.setScreenMac(header.getScreenMac());

        Response response = adapterClient.apkUpdateResultCallBack(requestDTO);


        return returnSuccess();

    }
}