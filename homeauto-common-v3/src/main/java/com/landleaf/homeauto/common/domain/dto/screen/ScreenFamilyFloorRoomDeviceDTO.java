package com.landleaf.homeauto.common.domain.dto.screen;

import lombok.Data;

import java.util.List;

/**
 * @author wenyilu
 * @ClassName 楼层房间设备信息
 **/
@Data
public class ScreenFamilyFloorRoomDeviceDTO {

    /**
     * 楼层名称
     */
    String name;
    /**
     * 几楼
     */
    Integer order;

    /**
     * 房间信息
     */
    List<ScreenFamilyRoomDTO> rooms;
}
