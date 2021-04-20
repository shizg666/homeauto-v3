package com.landleaf.homeauto.center.device.service;

import com.landleaf.homeauto.center.device.model.vo.space.SpaceManageStaticPageVO;
import com.landleaf.homeauto.center.device.model.vo.space.SpaceManageStaticQryDTO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;

/**
 * 空间管理业务类
 */
public interface ISpaceManageService {
    /**
     * 空間統計
     * @param spaceManageStaticQryDTO  查詢參數
     */
    BasePageVO<SpaceManageStaticPageVO> spaceManageStatistics(SpaceManageStaticQryDTO spaceManageStaticQryDTO);
}
