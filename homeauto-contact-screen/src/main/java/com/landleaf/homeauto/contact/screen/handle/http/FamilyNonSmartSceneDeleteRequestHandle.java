package com.landleaf.homeauto.contact.screen.handle.http;

import com.google.common.collect.Lists;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpDeleteNonSmartSceneDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpSceneResponseDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.controller.inner.remote.AdapterClient;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenFamilyScene;
import com.landleaf.homeauto.contact.screen.dto.payload.http.request.FamilyNonSmartSceneDeleteRequestPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.response.FamilyNonSmartSceneDeleteResponsePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

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

    public ContactScreenHttpResponse<List<FamilyNonSmartSceneDeleteResponsePayload>> handlerRequest(FamilyNonSmartSceneDeleteRequestPayload requestPayload) {

        List<FamilyNonSmartSceneDeleteResponsePayload> result = Lists.newArrayList();

        ContactScreenHeader header = ContactScreenContext.getContext();

        ScreenHttpDeleteNonSmartSceneDTO requestDTO = new ScreenHttpDeleteNonSmartSceneDTO();

        requestDTO.setScreenMac(header.getScreenMac());

        requestDTO.setSceneIds(requestPayload.getRequest());

        Response<List<ScreenHttpSceneResponseDTO>> responseDTO = null;
        try {
            responseDTO = adapterClient.deleteNonSmartScene(requestDTO);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (responseDTO != null && responseDTO.isSuccess()) {
            List<ContactScreenFamilyScene> contactScreenFamilyScenes = convertSceneResponse(responseDTO.getResult());
            if (!CollectionUtils.isEmpty(contactScreenFamilyScenes)) {
                result.addAll(contactScreenFamilyScenes.stream().map(i -> {
                    FamilyNonSmartSceneDeleteResponsePayload payload = new FamilyNonSmartSceneDeleteResponsePayload();
                    BeanUtils.copyProperties(i, payload);
                    return payload;
                }).collect(Collectors.toList()));
            }
            return returnSuccess(result);
        }

        return returnError();

    }


}