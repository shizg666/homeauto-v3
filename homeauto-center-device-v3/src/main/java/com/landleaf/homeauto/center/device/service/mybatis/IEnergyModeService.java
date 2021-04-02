package com.landleaf.homeauto.center.device.service.mybatis;

import com.landleaf.homeauto.center.device.model.dto.energy.EnergyModeDTO;

/**
 * @author wenyilu
 */
public interface IEnergyModeService {

    /**
     * 查询当前家庭所属能源站的模式
     * @param familyId
     * @return
     */
    EnergyModeDTO getEnergyModeValue(String familyId);
}
