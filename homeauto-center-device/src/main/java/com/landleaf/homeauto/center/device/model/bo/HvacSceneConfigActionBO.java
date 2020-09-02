package com.landleaf.homeauto.center.device.model.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 暖通场景配置动作业务对象
 *
 * @author Yujiumin
 * @version 2020/9/2
 */
@Data
@NoArgsConstructor
public class HvacSceneConfigActionBO {

    private String workMode;

    private String workTemperature;

    private String airSpeed;

}
