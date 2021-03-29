package com.landleaf.homeauto.contact.screen.dto.payload;

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
     * 类型
     */
    private Integer roomType;

    /**
     * 设备
     */
    private List<ContactScreenFamilyDeviceInfo> devices;

}
