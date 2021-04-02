package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.landleaf.homeauto.center.device.enums.EnergyModeEnum;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.realestate.HomeAutoRealestate;
import com.landleaf.homeauto.center.device.model.dto.energy.EnergyModeDTO;
import com.landleaf.homeauto.center.device.service.mybatis.IEnergyModeService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoRealestateService;
import com.landleaf.homeauto.common.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wenyilu
 */
@Service
public class EnergyModeServiceImpl implements IEnergyModeService {

    @Autowired
    private IHomeAutoRealestateService homeAutoRealestateService;
    @Autowired
    private IHomeAutoFamilyService homeAutoFamilyService;

    @Override
    public EnergyModeDTO getEnergyModeValue(String familyId) {
        EnergyModeDTO data = new EnergyModeDTO();
        data.setValue(EnergyModeEnum.OVER_SEASON.getCode());
        HomeAutoFamilyDO familyDO = homeAutoFamilyService.getById(familyId);
        if(familyDO!=null){
            HomeAutoRealestate realestate = homeAutoRealestateService.getById(familyDO.getRealestateId());
            if(realestate!=null&&realestate.getModeStatus()!=null){
                data.setValue(realestate.getModeStatus());
            }
        }
        return data;
    }


}
