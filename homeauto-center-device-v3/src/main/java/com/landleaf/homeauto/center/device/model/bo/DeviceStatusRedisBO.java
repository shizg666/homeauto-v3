package com.landleaf.homeauto.center.device.model.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设备状态业务对象
 *
 * @author 张洪滨
 * @version 2020/9/9
 */
@Data
@NoArgsConstructor
public class DeviceStatusRedisBO {

    private String key;

    private String statusValue;

}
