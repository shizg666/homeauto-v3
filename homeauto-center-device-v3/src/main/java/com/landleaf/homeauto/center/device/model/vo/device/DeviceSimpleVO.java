package com.landleaf.homeauto.center.device.model.vo.device;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yujiumin
 * @version 2020/8/24
 */
@Data
@NoArgsConstructor
@ApiModel("家庭设备简单视图对象")
public class DeviceSimpleVO {

    private String deviceId;

    private String deviceName;

    private String deviceIcon;

    private String productCode;

    private String categoryCode;

    private String position;

}
