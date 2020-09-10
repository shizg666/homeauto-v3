package com.landleaf.homeauto.center.device.model.vo.scene;

import com.landleaf.homeauto.center.device.model.vo.device.DeviceVO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/9/10
 */
@Data
@NoArgsConstructor
public class NonSmartRoomDeviceVO {

    private String roomName;

    private List<DeviceVO> devices;

}
