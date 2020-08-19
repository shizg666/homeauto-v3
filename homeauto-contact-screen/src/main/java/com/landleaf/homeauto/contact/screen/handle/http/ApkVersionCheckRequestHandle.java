package com.landleaf.homeauto.contact.screen.handle.http;

import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpApkVersionCheckDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpApkVersionCheckResponseDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.controller.inner.remote.AdapterClient;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.dto.payload.http.request.ApkUpdateResultRequestPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.response.ApkVersionCheckResponsePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 大屏apk更新检测
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class ApkVersionCheckRequestHandle extends AbstractHttpRequestHandler {

    @Autowired
    private AdapterClient adapterClient;

    public ContactScreenHttpResponse<ApkVersionCheckResponsePayload> handlerRequest(ApkUpdateResultRequestPayload requestPayload) {
        ApkVersionCheckResponsePayload result = new ApkVersionCheckResponsePayload();

        ContactScreenHeader header = ContactScreenContext.getContext();

        ScreenHttpApkVersionCheckDTO requestDTO = new ScreenHttpApkVersionCheckDTO();

        requestDTO.setVersion(requestPayload.getRequest());
        requestDTO.setScreenMac(header.getScreenMac());

        Response<ScreenHttpApkVersionCheckResponseDTO> response = null;
        try {
            response = adapterClient.apkVersionCheck(requestDTO);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (response != null && response.isSuccess()) {
            ScreenHttpApkVersionCheckResponseDTO tmpResult = response.getResult();
            BeanUtils.copyProperties(tmpResult, result);
            return returnSuccess(result);
        }

        return returnError();

    }
}