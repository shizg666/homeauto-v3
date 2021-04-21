package com.landleaf.homeauto.contact.screen.client.service;

import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpFamilyBindDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpHolidaysCheckDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpRequestDTO;
import com.landleaf.homeauto.contact.screen.client.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.client.dto.payload.http.request.*;

public interface HttpRequestService {

    ContactScreenHttpResponse floorRoomDeviceList(ScreenHttpRequestDTO requestDTO);

    ContactScreenHttpResponse smartSceneList(ScreenHttpRequestDTO requestDTO);

    ContactScreenHttpResponse smartSceneTimingList(ScreenHttpRequestDTO requestDTO);

    ContactScreenHttpResponse newsList(ScreenHttpRequestDTO requestDTO);

    ContactScreenHttpResponse weahter(ScreenHttpRequestDTO requestDTO);

    ContactScreenHttpResponse familyCode(ScreenHttpRequestDTO requestDTO);

    ContactScreenHttpResponse nonSmartSceneSaveOrUpdate(FamilySceneRequestSaveOrUpdateRequestPayload requestDTO, String screenMac);

    ContactScreenHttpResponse nonSmartSceneDelete(FamilySceneDeleteRequestPayload requestDTO, String screenMac);

    ContactScreenHttpResponse timingSceneSaveOrUpdate(FamilyTimingSceneSaveOrUpdateRequestPayload requestDTO, String screenMac);

    ContactScreenHttpResponse timingSceneDelete(FamilyTimingSceneDeleteRequestPayload requestDTO, String screenMac);

    ContactScreenHttpResponse holidaysCheck(HolidaysCheckRequestPayload requestPayload,String screenMac);

    ContactScreenHttpResponse apkVersionCheck(ApkVersionCheckRequestPayload requestDTO, String screenMac);

    ContactScreenHttpResponse familyBind(FamilyBindRequestPayload requestDTO,String screenMac);
}
