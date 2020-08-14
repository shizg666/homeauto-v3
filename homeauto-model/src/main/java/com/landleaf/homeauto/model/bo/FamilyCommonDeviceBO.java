package com.landleaf.homeauto.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 家庭常用设备业务对象
 *
 * @author Yujiumin
 * @version 2020/8/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamilyCommonDeviceBO {

    private String deviceId;

    private String deviceName;

    private String floorName;

    private String roomName;

    private String deviceIcon;

    private Integer index;
}
