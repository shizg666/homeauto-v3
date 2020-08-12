package com.landleaf.homeauto.contact.screen.handle.http;

import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpRequestDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpTimingSceneResponseDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenFamilyTimingScene;
import com.landleaf.homeauto.contact.screen.dto.payload.http.request.CommonHttpRequestPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.response.FamilySceneTimingRequestReplyPayload;
import com.landleaf.homeauto.contact.screen.controller.inner.remote.AdapterClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 定时场景(户式化)信息请求
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class SceneTimingRequestHandle extends AbstractHttpRequestHandler {


    @Autowired
    private AdapterClient adapterClient;

    public ContactScreenHttpResponse<FamilySceneTimingRequestReplyPayload> handlerRequest(CommonHttpRequestPayload requestPayload) {


        FamilySceneTimingRequestReplyPayload result = new FamilySceneTimingRequestReplyPayload();

        ContactScreenHeader header = ContactScreenContext.getContext();

        ScreenHttpRequestDTO requestDTO = new ScreenHttpRequestDTO();

        requestDTO.setFamilyCode(header.getFamilyCode());
        requestDTO.setScreenMac(header.getScreenMac());

        Response<List<ScreenHttpTimingSceneResponseDTO>> responseDTO = adapterClient.getTimingSceneList(requestDTO);

        List<ScreenHttpTimingSceneResponseDTO> tmpResult = responseDTO.getResult();
        List<ContactScreenFamilyTimingScene> data = tmpResult.stream().map(i -> {
            ContactScreenFamilyTimingScene timingScene = new ContactScreenFamilyTimingScene();
            BeanUtils.copyProperties(i, timingScene);
            return timingScene;

        }).collect(Collectors.toList());
        result.setData(data);
        return returnSuccess(result);

    }
}