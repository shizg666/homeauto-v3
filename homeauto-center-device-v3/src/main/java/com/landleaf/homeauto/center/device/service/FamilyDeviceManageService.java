package com.landleaf.homeauto.center.device.service;

import cn.jiguang.common.utils.StringUtils;
import com.landleaf.homeauto.center.device.model.vo.device.FamilyDeviceDetailVO;
import com.landleaf.homeauto.center.device.model.vo.device.FamilyDevicePageVO;
import com.landleaf.homeauto.center.device.model.vo.device.FamilyDeviceQryDTO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FamilyDeviceManageService implements IFamilyDeviceManageService {
    @Autowired
    private IHomeAutoFamilyService familyService;

    @Override
    public BasePageVO<FamilyDevicePageVO> listFamilyDevicePage(FamilyDeviceQryDTO familyDeviceQryDTO) {
        Long realestateId = familyDeviceQryDTO.getRealestateId();
        Long projectId = familyDeviceQryDTO.getProjectId();
        String buildingCode = familyDeviceQryDTO.getBuildingCode();
        if (Objects.isNull(realestateId) || Objects.isNull(projectId) || StringUtils.isEmpty(buildingCode)) {
            throw new BusinessException(ErrorCodeEnumConst.FAMILY_DEVICE_PAGE_MANAGE_STATICS_PARAM);
        }
        return familyService.listFamilyDevicePage(familyDeviceQryDTO);
    }

    @Override
    public FamilyDeviceDetailVO getFamilyDeviceDetail(Long familyId, Long deviceId) {
        return familyService.getFamilyDeviceDetail(familyId,deviceId);
    }
}
