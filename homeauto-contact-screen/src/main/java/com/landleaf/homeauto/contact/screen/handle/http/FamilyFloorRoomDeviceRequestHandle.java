package com.landleaf.homeauto.contact.screen.handle.http;

import com.google.common.collect.Lists;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenFamilyDeviceInfoDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenFamilyRoomDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpRequestDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpFloorRoomDeviceResponseDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.controller.inner.remote.AdapterClient;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenFamilyDeviceInfo;
import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenFamilyRoom;
import com.landleaf.homeauto.contact.screen.dto.payload.http.request.CommonHttpRequestPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.response.FamilyRoomDeviceResponsePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 楼层房间设备信息更新payload
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class FamilyFloorRoomDeviceRequestHandle extends AbstractHttpRequestHandler {

    @Autowired
    private AdapterClient adapterClient;


    public ContactScreenHttpResponse<List<FamilyRoomDeviceResponsePayload>> handlerRequest(CommonHttpRequestPayload requestPayload) {

        List<FamilyRoomDeviceResponsePayload> data = Lists.newArrayList();
        ContactScreenHeader header = ContactScreenContext.getContext();

        ScreenHttpRequestDTO requestDTO = new ScreenHttpRequestDTO();

        requestDTO.setScreenMac(header.getScreenMac());
        Response<List<ScreenHttpFloorRoomDeviceResponseDTO>> responseDTO = null;
        try {
            responseDTO = adapterClient.getFloorRoomDeviceList(requestDTO);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (responseDTO != null && responseDTO.isSuccess()) {
            List<ScreenHttpFloorRoomDeviceResponseDTO> tempResult = responseDTO.getResult();
            if (!CollectionUtils.isEmpty(tempResult)) {
                data = tempResult.stream().map(i -> {
                    FamilyRoomDeviceResponsePayload familyFloorRoomDevice = new FamilyRoomDeviceResponsePayload();
                    List<ScreenFamilyRoomDTO> rooms = i.getRooms();
                    List<ContactScreenFamilyRoom> tmpRooms = rooms.stream().map(r -> {
                        ContactScreenFamilyRoom familyRoom = new ContactScreenFamilyRoom();
                        List<ScreenFamilyDeviceInfoDTO> devices = r.getDevices();
                        List<ContactScreenFamilyDeviceInfo> tmpDevices = devices.stream().map(d -> {
                            ContactScreenFamilyDeviceInfo deviceInfo = new ContactScreenFamilyDeviceInfo();
                            BeanUtils.copyProperties(d, deviceInfo);
                            return deviceInfo;
                        }).collect(Collectors.toList());
                        familyRoom.setRoomName(r.getRoomName());
                        familyRoom.setRoomType(r.getRoomType());
                        familyRoom.setDevices(tmpDevices);
                        return familyRoom;
                    }).collect(Collectors.toList());
                    familyFloorRoomDevice.setName(i.getName());
                    familyFloorRoomDevice.setOrder(i.getOrder());
                    familyFloorRoomDevice.setRooms(tmpRooms);
                    return familyFloorRoomDevice;
                }).collect(Collectors.toList());

            }
            return returnSuccess(data);
        }

        return returnError();

    }


}