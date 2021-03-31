package com.landleaf.homeauto.contact.screen.dto.payload;

import lombok.Data;

import java.util.List;

/**
 * @author wenyilu
 * @ClassName 楼层信息
 **/
@Data
public class ContactScreenFamilyRoomDevice {

    /**
     * 楼层名称
     */
    String name;
    /**
     * 几楼
     */
    String floor;

    /**
     * 房间信息
     */
    List<ContactScreenFamilyRoom> rooms;

}
