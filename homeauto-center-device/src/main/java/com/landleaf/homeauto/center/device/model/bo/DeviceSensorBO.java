package com.landleaf.homeauto.center.device.model.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 传感器设备业务对象
 *
 * @author Yujiumin
 * @version 2020/8/26
 */
@Data
@Deprecated
@NoArgsConstructor
public class DeviceSensorBO {

    private String familyCode;

    private String productCode;

    private String deviceSn;

    private List<String> attributeList;

}
