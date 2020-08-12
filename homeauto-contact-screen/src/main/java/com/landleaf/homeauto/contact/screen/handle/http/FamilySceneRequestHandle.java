package com.landleaf.homeauto.contact.screen.handle.http;

import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpRequestDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpSceneResponseDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenFamilyScene;
import com.landleaf.homeauto.contact.screen.dto.payload.http.request.CommonHttpRequestPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.response.FamilySceneRequestReplyPayload;
import com.landleaf.homeauto.contact.screen.controller.inner.remote.AdapterClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public ContactScreenHttpResponse<FamilySceneRequestReplyPayload> handlerRequest(CommonHttpRequestPayload requestPayload) {


        FamilySceneRequestReplyPayload result = new FamilySceneRequestReplyPayload();

        ContactScreenHeader header = ContactScreenContext.getContext();

        ScreenHttpRequestDTO requestDTO = new ScreenHttpRequestDTO();

        requestDTO.setFamilyCode(header.getFamilyCode());
        requestDTO.setScreenMac(header.getScreenMac());

        Response<List<ScreenHttpSceneResponseDTO>> responseDTO = adapterClient.getSceneList(requestDTO);

        List<ScreenHttpSceneResponseDTO> tmpResult = responseDTO.getResult();
        List<ContactScreenFamilyScene> data = tmpResult.stream().map(i -> {
            ContactScreenFamilyScene scene = new ContactScreenFamilyScene();
            BeanUtils.copyProperties(i, scene);
            return scene;

        }).collect(Collectors.toList());

        result.setData(data);

        return returnSuccess(result);

    }


}