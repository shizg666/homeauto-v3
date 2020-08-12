package com.landleaf.homeauto.common.domain.dto.screen.http.response;

import com.landleaf.homeauto.common.domain.dto.screen.ScreenFamilyFloorRoomDeviceDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenFamilyRoomDTO;
import lombok.Data;

import java.util.List;

/**
 * 楼层房间设备信息请求返回
 *
 * @author wenyilu
 */
@Data
public class ScreenHttpFloorRoomDeviceResponseDTO {


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
