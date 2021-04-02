package com.landleaf.homeauto.contact.screen.client.dto.payload;

import lombok.Data;

import java.util.List;

/**
 * @author wenyilu
 * @ClassName 房间信息
 **/
@Data
public class ContactScreenFamilyRoom {

    /**
     * 房间名称
     */
    private String roomName;

    /**
     * 房间名称
     */
    private String roomType;

    /**
     * 设备
     */
    private List<ContactScreenFamilyDeviceInfo> devices;

}
