package com.landleaf.homeauto.contact.screen.handle.http;

import com.baomidou.mybatisplus.extension.api.R;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.ContactScreenFamilyDeviceAttrInfoDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenFamilyDeviceInfoDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenFamilyDeviceInfoProtocolDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenFamilyRoomDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpProjectDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpProjectHouseTypeDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenFamilyModelResponseDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpFloorRoomDeviceResponseDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpFloorRoomDeviceSceneResponseDTO;
import com.landleaf.homeauto.contact.screen.controller.inner.remote.AdapterClient;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenFamilyDeviceAttrInfo;
import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenFamilyDeviceInfo;
import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenFamilyDeviceInfoProtocol;
import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenFamilyRoom;
import com.landleaf.homeauto.contact.screen.dto.payload.http.request.ProjectHouseTypeRequestPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.response.FamilyRoomDeviceResponsePayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.response.FamilySceneResponsePayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.response.ProjectTemplateResponsePayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.response.TemplateRoomDeviceSceneResponsePayload;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author Lokiy
 * @Date 2021/8/9 13:52
 * @Description 项目模板配置
 */
@Component
@Slf4j
public class TemplateConfigRequestHandle extends AbstractHttpRequestHandler{

    @Autowired
    private AdapterClient adapterClient;

    public ContactScreenHttpResponse<TemplateRoomDeviceSceneResponsePayload> handlerRequest(ProjectHouseTypeRequestPayload requestPayload) {

        ScreenHttpProjectHouseTypeDTO requestDTO = new ScreenHttpProjectHouseTypeDTO(requestPayload.getProjectCode(), requestPayload.getHouseType());

        Response<ScreenHttpFloorRoomDeviceSceneResponseDTO> responseDTO = null;
        try {
            responseDTO = adapterClient.getTemplateConfig(requestDTO);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (responseDTO != null && responseDTO.isSuccess()) {
            ScreenHttpFloorRoomDeviceSceneResponseDTO tempResult = responseDTO.getResult();
            return returnSuccess(convertDto2Payload(tempResult));
        }
        return returnError(responseDTO);
    }

    private TemplateRoomDeviceSceneResponsePayload convertDto2Payload(ScreenHttpFloorRoomDeviceSceneResponseDTO tempResult) {
        List<FamilyRoomDeviceResponsePayload> floors = convert2Floors(tempResult.getFloors());
        List<FamilySceneResponsePayload> scenes = tempResult.getScenes().stream().map(t -> {
            FamilySceneResponsePayload payload = new FamilySceneResponsePayload();
            BeanUtils.copyProperties(t, payload);
            return payload;
        }).collect(Collectors.toList());

        TemplateRoomDeviceSceneResponsePayload result = new TemplateRoomDeviceSceneResponsePayload();
        result.setFloors(floors);
        result.setScenes(scenes);
        return result;
    }

    private List<FamilyRoomDeviceResponsePayload> convert2Floors(List<ScreenHttpFloorRoomDeviceResponseDTO> tempResult) {
        List<FamilyRoomDeviceResponsePayload> data = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(tempResult)) {
            data = tempResult.stream().map(i -> {
                FamilyRoomDeviceResponsePayload familyFloorRoomDevice = new FamilyRoomDeviceResponsePayload();
                List<ScreenFamilyRoomDTO> rooms = i.getRooms();
                familyFloorRoomDevice.setRooms(convertDtoRoom2Payload(rooms));
                familyFloorRoomDevice.setFloorName(i.getFloor());
                return familyFloorRoomDevice;
            }).collect(Collectors.toList());
            data.sort(Comparator.comparing(FamilyRoomDeviceResponsePayload::getFloorName));
            for (int i = 0; i < data.size(); i++) {
                data.get(i).setFloor(i+1);
            }
        }
        return data;
    }


    private List<ContactScreenFamilyRoom> convertDtoRoom2Payload(List<ScreenFamilyRoomDTO> rooms) {
        List<ContactScreenFamilyRoom> tmpRooms = Lists.newArrayList();
        if(!CollectionUtils.isEmpty(rooms)){
            tmpRooms = rooms.stream().map(r -> {
                ContactScreenFamilyRoom familyRoom = new ContactScreenFamilyRoom();
                List<ScreenFamilyDeviceInfoDTO> devices = r.getDevices();
                familyRoom.setDevices(convertDtoDevice2Payload(devices));
                familyRoom.setRoomName(r.getRoomName());
                familyRoom.setRoomType(r.getRoomType());
                return familyRoom;
            }).collect(Collectors.toList());
        }
        return tmpRooms;
    }

    private List<ContactScreenFamilyDeviceInfo> convertDtoDevice2Payload(List<ScreenFamilyDeviceInfoDTO> devices) {
        List<ContactScreenFamilyDeviceInfo> tmpDevices = Lists.newArrayList();
        if(!CollectionUtils.isEmpty(devices)){
            tmpDevices = devices.stream().map(d -> {
                ContactScreenFamilyDeviceInfo deviceInfo = new ContactScreenFamilyDeviceInfo();
                BeanUtils.copyProperties(d, deviceInfo);
                List<ContactScreenFamilyDeviceAttrInfoDTO> attrs = d.getAttrs();
                if(!CollectionUtils.isEmpty(attrs)){
                    deviceInfo.setAttrs(attrs.stream().map(i->{
                        ContactScreenFamilyDeviceAttrInfo attrInfo = new ContactScreenFamilyDeviceAttrInfo();
                        BeanUtils.copyProperties(i,attrInfo);
                        attrInfo.setAttrTag(i.getAttrCode());
                        return attrInfo;
                    }).collect(Collectors.toList()));
                }
                ScreenFamilyDeviceInfoProtocolDTO deviceProtocol = d.getDeviceProtocol();
                if(!Objects.isNull(deviceProtocol)){
                    ContactScreenFamilyDeviceInfoProtocol target = new ContactScreenFamilyDeviceInfoProtocol();
                    BeanUtils.copyProperties(deviceProtocol,target);
                    deviceInfo.setDeviceProtocol(target);
                }
                return deviceInfo;
            }).collect(Collectors.toList());
        }
        return tmpDevices;
    }

}
