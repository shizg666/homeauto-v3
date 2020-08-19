package com.landleaf.homeauto.contact.screen.client.dto.payload.http.response;

import com.landleaf.homeauto.contact.screen.client.dto.payload.ContactScreenFamilyRoomDevice;
import lombok.Data;

import java.util.List;

/**
 * 楼层房间设备信息更新payload
 *
 * @author wenyilu
 */
@Data
public class FamilyRoomDeviceRequestReplyPayload {


    List<ContactScreenFamilyRoomDevice> data;


}
