package com.landleaf.homeauto.center.device.model.bo;

import com.google.common.base.Objects;
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
@Deprecated
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FamilyDeviceWithPositionBO that = (FamilyDeviceWithPositionBO) o;
        return Objects.equal(deviceId, that.deviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(deviceId, deviceSn, deviceName, floorName, roomName, deviceIcon, index);
    }
}
