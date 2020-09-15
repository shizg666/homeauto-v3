package com.landleaf.homeauto.contact.screen.handle.http;

import com.google.common.collect.Lists;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpRequestDTO;
import com.landleaf.homeauto.common.domain.dto.sync.SyncSceneInfoDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.controller.inner.remote.AdapterClient;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenFamilyScene;
import com.landleaf.homeauto.contact.screen.dto.payload.http.request.CommonHttpRequestPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.response.FamilySceneResponsePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 场景（户式化）请求
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class FamilySceneRequestHandle extends AbstractHttpRequestHandler {


    @Autowired
    private AdapterClient adapterClient;

    public ContactScreenHttpResponse<List<FamilySceneResponsePayload>> handlerRequest(CommonHttpRequestPayload requestPayload) {
        List<FamilySceneResponsePayload> result = Lists.newArrayList();

        ContactScreenHeader header = ContactScreenContext.getContext();

        ScreenHttpRequestDTO requestDTO = new ScreenHttpRequestDTO();

        requestDTO.setScreenMac(header.getScreenMac());

        Response<List<SyncSceneInfoDTO>> responseDTO = null;
        try {
            responseDTO = adapterClient.getSceneList(requestDTO);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (responseDTO != null && responseDTO.isSuccess()) {
            List<ContactScreenFamilyScene> contactScreenFamilyScenes = convertSceneResponse(responseDTO.getResult());
            if (!CollectionUtils.isEmpty(contactScreenFamilyScenes)) {
                result.addAll(contactScreenFamilyScenes.stream().map(i -> {
                    FamilySceneResponsePayload payload = new FamilySceneResponsePayload();
                    BeanUtils.copyProperties(i, payload);
                    return payload;
                }).collect(Collectors.toList()));
            }
            return returnSuccess(result);
        }

        return returnError(responseDTO);

    }


}