package com.landleaf.homeauto.contact.screen.handle.http;

import com.google.common.collect.Lists;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpSaveOrUpdateSceneDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpSceneActionDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpSceneResponseDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.controller.inner.remote.AdapterClient;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenFamilyScene;
import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenSceneAction;
import com.landleaf.homeauto.contact.screen.dto.payload.http.request.FamilySceneRequestSaveOrUpdateRequestPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.response.FamilySceneSaveOrUpdateResponsePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 场景（自由方舟）新增修改请求
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class FamilySceneSaveOrUpdateRequestHandle extends AbstractHttpRequestHandler {

    @Autowired
    private AdapterClient adapterClient;

    public ContactScreenHttpResponse<List<FamilySceneSaveOrUpdateResponsePayload>> handlerRequest(FamilySceneRequestSaveOrUpdateRequestPayload requestPayload) {


        List<FamilySceneSaveOrUpdateResponsePayload> result = Lists.newArrayList();

        ContactScreenHeader header = ContactScreenContext.getContext();


        List<ContactScreenFamilyScene> tmpRequestData = requestPayload.getRequest();
        List<ScreenHttpSaveOrUpdateSceneDTO> requestData = tmpRequestData.stream().map(i -> {
            ScreenHttpSaveOrUpdateSceneDTO requestDTO = new ScreenHttpSaveOrUpdateSceneDTO();
            BeanUtils.copyProperties(i, requestDTO);
            requestDTO.setScreenMac(header.getScreenMac());
            List<ContactScreenSceneAction> actions = i.getActions();
            requestDTO.setActions(actions.stream().map(a -> {
                ScreenHttpSceneActionDTO actionDTO = new ScreenHttpSceneActionDTO();
                BeanUtils.copyProperties(a, actionDTO);
                return actionDTO;
            }).collect(Collectors.toList()));
            return requestDTO;
        }).collect(Collectors.toList());

        Response<List<ScreenHttpSceneResponseDTO>> responseDTO = null;
        try {
            responseDTO = adapterClient.saveOrUpdateScene(requestData);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (responseDTO != null && responseDTO.isSuccess()) {
            List<ContactScreenFamilyScene> contactScreenFamilyScenes = convertSceneResponse(responseDTO.getResult());
            if (!CollectionUtils.isEmpty(contactScreenFamilyScenes)) {
                result.addAll(contactScreenFamilyScenes.stream().map(i -> {
                    FamilySceneSaveOrUpdateResponsePayload payload = new FamilySceneSaveOrUpdateResponsePayload();
                    BeanUtils.copyProperties(i, payload);
                    return payload;
                }).collect(Collectors.toList()));
            }
            return returnSuccess(result);
        }

        return returnError(responseDTO);

    }


}