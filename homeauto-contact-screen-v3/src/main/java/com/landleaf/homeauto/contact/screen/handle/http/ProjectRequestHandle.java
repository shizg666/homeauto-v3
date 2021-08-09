package com.landleaf.homeauto.contact.screen.handle.http;

import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpProjectDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpRequestDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenFamilyModelResponseDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpFloorRoomDeviceResponseDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.controller.inner.remote.AdapterClient;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.dto.payload.http.request.CommonHttpRequestPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.request.ProjectRequestPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.response.FamilyRoomDeviceResponsePayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.response.ProjectTemplateResponsePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Lokiy
 * @Date 2021/8/9 13:52
 * @Description 项目请求相关处理类
 */
@Component
@Slf4j
public class ProjectRequestHandle extends AbstractHttpRequestHandler{

    @Autowired
    private AdapterClient adapterClient;

    public ContactScreenHttpResponse<List<ProjectTemplateResponsePayload>> handlerRequest(ProjectRequestPayload requestPayload) {

        ScreenHttpProjectDTO requestDTO = new ScreenHttpProjectDTO(requestPayload.getProjectCode());

        Response<List<ScreenFamilyModelResponseDTO>> responseDTO = null;
        try {
            responseDTO = adapterClient.getProjectTemplate(requestDTO);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (responseDTO != null && responseDTO.isSuccess()) {
            List<ScreenFamilyModelResponseDTO> tempResult = responseDTO.getResult();
            return returnSuccess(convertDto2Payload(tempResult));
        }
        return returnError(responseDTO);
    }

    private List<ProjectTemplateResponsePayload> convertDto2Payload(List<ScreenFamilyModelResponseDTO> tempResult) {
        return tempResult.stream().map(t -> {
            ProjectTemplateResponsePayload payload = new ProjectTemplateResponsePayload();
            BeanUtils.copyProperties(t, payload);
            return payload;
        }).collect(Collectors.toList());
    }

}
