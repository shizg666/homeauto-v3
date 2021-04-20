package com.landleaf.homeauto.center.device.service;

import com.landleaf.homeauto.center.device.model.vo.device.FamilyDevicePageVO;
import com.landleaf.homeauto.center.device.model.vo.device.FamilyDeviceQryDTO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;

/**
 * 家庭设备管理业务类
 */
public interface IFamilyDeviceManageService {

    /**
     * 分页查询家庭设备
     * @param familyDeviceQryDTO
     * @return
     */
    BasePageVO<FamilyDevicePageVO> listFamilyDevicePage(FamilyDeviceQryDTO familyDeviceQryDTO);
}
