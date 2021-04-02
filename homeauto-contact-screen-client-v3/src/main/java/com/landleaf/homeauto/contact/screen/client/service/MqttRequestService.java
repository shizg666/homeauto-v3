package com.landleaf.homeauto.contact.screen.client.service;

import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpHolidaysCheckDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpRequestDTO;
import com.landleaf.homeauto.contact.screen.client.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.client.dto.payload.http.request.*;
import com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.upload.DeviceStatusUpdateRequestPayload;
import com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.upload.FamilyEventAlarmPayload;
import com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.upload.ScreenSceneSetRequestPayload;

public interface MqttRequestService {

    void uploadControlScene(String screenMac, ScreenSceneSetRequestPayload payload);

    void uploadDeviceStatus(String screenMac, DeviceStatusUpdateRequestPayload payload);

    void uploadAlarmEvent(String screenMac, FamilyEventAlarmPayload payload);
}
