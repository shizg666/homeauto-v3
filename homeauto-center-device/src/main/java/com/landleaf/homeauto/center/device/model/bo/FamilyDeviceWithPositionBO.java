package com.landleaf.homeauto.center.device.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 带有位置信息的家庭设备业务对象
 *
 * @author Yujiumin
 * @version 2020/8/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamilyDeviceWithPositionBO {

    private String deviceId;

    private String deviceSn;

    private String deviceName;

    private String floorName;

    private String roomName;

    private String deviceIcon;

    private Integer index;
}
