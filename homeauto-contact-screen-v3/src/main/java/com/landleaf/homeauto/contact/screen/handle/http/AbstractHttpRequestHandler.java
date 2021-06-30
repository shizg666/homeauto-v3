package com.landleaf.homeauto.contact.screen.handle.http;

import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpTimingSceneResponseDTO;
import com.landleaf.homeauto.common.domain.dto.sync.SyncSceneInfoDTO;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenErrorCodeEnumConst;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenFamilyScene;
import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenFamilyTimingScene;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wenyilu
 */
public abstract class AbstractHttpRequestHandler {


    public ContactScreenHttpResponse returnSuccess() {
        return returnSuccess(ContactScreenErrorCodeEnumConst.SUCCESS.getMsg());
    }

    public ContactScreenHttpResponse returnSuccess(String successMsg) {
        return returnSuccess(null, successMsg);
    }

    public ContactScreenHttpResponse returnSuccess(Object object) {
        return returnSuccess(object, ContactScreenErrorCodeEnumConst.SUCCESS.getMsg());
    }

    public ContactScreenHttpResponse returnError() {
        ContactScreenHttpResponse response = new ContactScreenHttpResponse();
        ContactScreenErrorCodeEnumConst systemError = ContactScreenErrorCodeEnumConst.SYSTEM_ERROR;
        response.setCode(systemError.getCode());
        response.setMessage(systemError.getMsg());
        response.setData(null);
        return response;
    }

    public ContactScreenHttpResponse returnError(Response response) {
        ContactScreenHttpResponse screenHttpResponse = new ContactScreenHttpResponse();
        if (response != null && !response.isSuccess()) {
//            screenHttpResponse.setMessage(String.format("%s:%s", response.getErrorCode(), response.getErrorMsg()));
            screenHttpResponse.setMessage( response.getErrorMsg());
        }
        screenHttpResponse.setCode(ContactScreenErrorCodeEnumConst.SYSTEM_ERROR.getCode());
        String errorMsg = response.getErrorMsg();
        if(!StringUtils.isEmpty(errorMsg)){
            if(StringUtils.equals(ContactScreenErrorCodeEnumConst.FAMILY_NOT_FOUND.getMsg(),errorMsg)){
                screenHttpResponse.setCode(ContactScreenErrorCodeEnumConst.FAMILY_NOT_FOUND.getCode());
            }else if(StringUtils.equals(ContactScreenErrorCodeEnumConst.MAC_ALREADY_BIND.getMsg(),errorMsg)){
                screenHttpResponse.setCode(ContactScreenErrorCodeEnumConst.MAC_ALREADY_BIND.getCode());
            }else if(StringUtils.equals(ContactScreenErrorCodeEnumConst.FAMILY_NOT_EXIST.getMsg(),errorMsg)){
                screenHttpResponse.setCode(ContactScreenErrorCodeEnumConst.FAMILY_NOT_EXIST.getCode());
            }
        }
        screenHttpResponse.setData(null);
        return screenHttpResponse;
    }

    public ContactScreenHttpResponse returnSuccess(Object object, String successMsg) {
        ContactScreenHttpResponse response = new ContactScreenHttpResponse();
        response.setCode(ContactScreenErrorCodeEnumConst.SUCCESS.getCode());
        response.setMessage(successMsg);
        response.setData(object);
        return response;
    }

    /**
     * 转换返回场景为大屏接收场景对象
     *
     * @param sceneResponseDTOS
     * @return
     */
    List<ContactScreenFamilyScene> convertSceneResponse(List<SyncSceneInfoDTO> sceneResponseDTOS) {
        List<ContactScreenFamilyScene> data = sceneResponseDTOS.stream().map(i -> {
            ContactScreenFamilyScene scene = new ContactScreenFamilyScene();
            BeanUtils.copyProperties(i, scene);
            return scene;

        }).collect(Collectors.toList());
        return data;
    }

    /**
     * 转换返回定时场景配置为大屏接收定时场景配置对象
     *
     * @param timingSceneResponseDTOS
     * @return
     */
    List<ContactScreenFamilyTimingScene> convertTimingSceneResponse(List<ScreenHttpTimingSceneResponseDTO> timingSceneResponseDTOS) {
        List<ContactScreenFamilyTimingScene> data = timingSceneResponseDTOS.stream().map(i -> {
            ContactScreenFamilyTimingScene scene = new ContactScreenFamilyTimingScene();
            BeanUtils.copyProperties(i, scene);
            return scene;

        }).collect(Collectors.toList());
        return data;
    }


}
