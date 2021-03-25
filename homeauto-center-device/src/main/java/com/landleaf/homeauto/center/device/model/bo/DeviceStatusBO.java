package com.landleaf.homeauto.center.device.model.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设备状态业务对象
 *
 * @author Yujiumin
 * @version 2020/8/25
 */
@Data
@Deprecated
@NoArgsConstructor
public class DeviceStatusBO {

    private String familyId;

    private String familyCode;

    private String productCode;

    private String categoryCode;

    private String deviceCode;

    private String statusCode;

    private String statusValue;

}
