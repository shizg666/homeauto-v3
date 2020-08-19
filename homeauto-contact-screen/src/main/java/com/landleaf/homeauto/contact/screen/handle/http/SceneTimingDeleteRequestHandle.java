package com.landleaf.homeauto.contact.screen.handle.http;

import com.google.common.collect.Lists;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpDeleteTimingSceneDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpTimingSceneResponseDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.controller.inner.remote.AdapterClient;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenFamilyTimingScene;
import com.landleaf.homeauto.contact.screen.dto.payload.http.request.FamilyTimingSceneDeleteRequestPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.response.FamilySceneTimingDeleteResponsePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 定时场景(户式化)信息请求
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class SceneTimingDeleteRequestHandle extends AbstractHttpRequestHandler {


    @Autowired
    private AdapterClient adapterClient;

    public ContactScreenHttpResponse<List<FamilySceneTimingDeleteResponsePayload>> handlerRequest(FamilyTimingSceneDeleteRequestPayload requestPayload) {


        List<FamilySceneTimingDeleteResponsePayload> result = Lists.newArrayList();

        ContactScreenHeader header = ContactScreenContext.getContext();

        ScreenHttpDeleteTimingSceneDTO requestDTO = new ScreenHttpDeleteTimingSceneDTO();

        requestDTO.setScreenMac(header.getScreenMac());
        requestDTO.setIds(requestPayload.getRequest());

        Response<List<ScreenHttpTimingSceneResponseDTO>> responseDTO = null;
        try {
            responseDTO = adapterClient.deleteTimingScene(requestDTO);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (responseDTO != null && responseDTO.isSuccess()) {
            List<ContactScreenFamilyTimingScene> contactScreenFamilyTimingScenes = convertTimingSceneResponse(responseDTO.getResult());
            if (!CollectionUtils.isEmpty(contactScreenFamilyTimingScenes)) {
                result.addAll(contactScreenFamilyTimingScenes.stream().map(i -> {
                    FamilySceneTimingDeleteResponsePayload payload = new FamilySceneTimingDeleteResponsePayload();
                    BeanUtils.copyProperties(i, payload);
                    return payload;
                }).collect(Collectors.toList()));
            }
            return returnSuccess(result);
        }

        return returnError();
    }
}