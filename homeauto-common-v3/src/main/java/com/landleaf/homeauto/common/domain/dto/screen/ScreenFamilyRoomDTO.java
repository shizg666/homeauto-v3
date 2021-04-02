package com.landleaf.homeauto.common.domain.dto.screen;

import lombok.Data;

import java.util.List;

/**
 * @author wenyilu
 * @ClassName 房间信息
 **/
@Data
public class ScreenFamilyRoomDTO {

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
    private List<ScreenFamilyDeviceInfoDTO> devices;

}
