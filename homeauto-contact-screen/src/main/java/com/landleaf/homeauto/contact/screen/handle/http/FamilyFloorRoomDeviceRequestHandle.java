package com.landleaf.homeauto.contact.screen.handle.http;

import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenFamilyDeviceInfoDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenFamilyRoomDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpRequestDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpFloorRoomDeviceResponseDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenFamilyDeviceInfo;
import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenFamilyRoom;
import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenFamilyRoomDevice;
import com.landleaf.homeauto.contact.screen.dto.payload.http.request.CommonHttpRequestPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.response.FamilyRoomDeviceRequestReplyPayload;
import com.landleaf.homeauto.contact.screen.controller.inner.remote.AdapterClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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


    public ContactScreenHttpResponse<FamilyRoomDeviceRequestReplyPayload> handlerRequest(CommonHttpRequestPayload requestPayload) {

        FamilyRoomDeviceRequestReplyPayload result = new FamilyRoomDeviceRequestReplyPayload();

        ContactScreenHeader header = ContactScreenContext.getContext();

        ScreenHttpRequestDTO requestDTO = new ScreenHttpRequestDTO();

        requestDTO.setFamilyCode(header.getFamilyCode());
        requestDTO.setScreenMac(header.getScreenMac());

        Response<List<ScreenHttpFloorRoomDeviceResponseDTO>> responseDTO = adapterClient.getFloorRoomDeviceList(requestDTO);

        List<ScreenHttpFloorRoomDeviceResponseDTO> tempResult = responseDTO.getResult();

        List<ContactScreenFamilyRoomDevice> data = tempResult.stream().map(i -> {
            ContactScreenFamilyRoomDevice familyFloorRoomDevice = new ContactScreenFamilyRoomDevice();
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

        result.setData(data);

        return returnSuccess(result);

    }


}