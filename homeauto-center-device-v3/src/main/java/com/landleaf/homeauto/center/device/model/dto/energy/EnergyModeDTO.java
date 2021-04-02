package com.landleaf.homeauto.center.device.model.dto.energy;

import com.landleaf.homeauto.center.device.enums.EnergyModeEnum;
import lombok.Data;

/**
 * @ClassName EnergyModeDTO
 * @Description: 能源站模式
 * @Author wyl
 * @Date 2020/9/25
 * @Version V1.0
 **/
@Data
public class EnergyModeDTO {

    /**
     * 当前值
     * {@link EnergyModeEnum}
     */
    private String value;

}
