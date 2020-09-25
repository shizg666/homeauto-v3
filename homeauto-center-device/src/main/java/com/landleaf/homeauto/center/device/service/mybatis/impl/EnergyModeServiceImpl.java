package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.landleaf.homeauto.center.device.enums.EnergyModeEnum;
import com.landleaf.homeauto.center.device.model.dto.energy.EnergyModeDTO;
import com.landleaf.homeauto.center.device.service.mybatis.IEnergyModeService;
import com.landleaf.homeauto.common.util.RandomUtil;
import org.springframework.stereotype.Service;

/**
 * @author wenyilu
 */
@Service
public class EnergyModeServiceImpl implements IEnergyModeService {


    @Override
    public EnergyModeDTO getEnergyModeValue(String familyId) {
        EnergyModeDTO data = new EnergyModeDTO();

        data.setValue(EnergyModeEnum.values()[RandomUtil.getRadomNum(0, 2)].getCode());
        return data;
    }


}
