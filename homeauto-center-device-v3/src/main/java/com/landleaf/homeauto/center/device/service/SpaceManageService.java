package com.landleaf.homeauto.center.device.service;

import com.landleaf.homeauto.center.device.model.vo.space.SpaceManageStaticPageVO;
import com.landleaf.homeauto.center.device.model.vo.space.SpaceManageStaticQryDTO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SpaceManageService implements ISpaceManageService {
    @Autowired
    private IHomeAutoFamilyService familyService;

    @Override
    public BasePageVO<SpaceManageStaticPageVO> spaceManageStatistics(SpaceManageStaticQryDTO spaceManageStaticQryDTO) {
        Long realestateId = spaceManageStaticQryDTO.getRealestateId();
        Long projectId = spaceManageStaticQryDTO.getProjectId();
        if (Objects.isNull(realestateId) || Objects.isNull(projectId)) {
            throw new BusinessException(ErrorCodeEnumConst.SPACE_MANAGE_STATICS_PARAM);
        }
        return familyService.spaceManageStatistics(spaceManageStaticQryDTO);
    }
}
