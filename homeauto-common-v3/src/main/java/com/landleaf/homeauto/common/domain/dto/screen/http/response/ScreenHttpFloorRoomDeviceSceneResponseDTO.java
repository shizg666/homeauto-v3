package com.landleaf.homeauto.common.domain.dto.screen.http.response;

import com.landleaf.homeauto.common.domain.dto.sync.SyncSceneInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author Lokiy
 * @Date 2021/8/9 15:16
 * @Description 大屏楼层房间设备场景返回
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScreenHttpFloorRoomDeviceSceneResponseDTO {

    /**
     * 楼层房间设备配置
     */
    private List<ScreenHttpFloorRoomDeviceResponseDTO> floors;

    /**
     * 场景配置
     */
    private List<SyncSceneInfoDTO> scenes;
}
