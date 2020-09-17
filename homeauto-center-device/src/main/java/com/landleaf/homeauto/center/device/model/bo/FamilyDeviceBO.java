package com.landleaf.homeauto.center.device.model.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yujiumin
 * @version 2020/8/24
 */
@Data
@NoArgsConstructor
public class FamilyDeviceBO {

    private String deviceId;

    private String deviceName;

    private String devicePicUrl;

    private String productCode;

    private String categoryCode;

}
