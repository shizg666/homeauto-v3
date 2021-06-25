package com.landleaf.homeauto.center.device.service.mybatis;

import com.landleaf.homeauto.center.device.model.vo.familymanager.FamilyManageDetailVO;
import com.landleaf.homeauto.center.device.model.vo.familymanager.FamilyManagerDTO;
import com.landleaf.homeauto.center.device.model.vo.familymanager.FamilyManagerPageVO;
import com.landleaf.homeauto.center.device.model.vo.familymanager.FamilyManagerQryVO;
import com.landleaf.homeauto.center.device.model.vo.statistics.KanBanStatistics;
import com.landleaf.homeauto.center.device.model.vo.statistics.KanBanStatisticsQry;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;

import java.util.List;

/**
 * <p>
 * 看板
 * </p>
 *
 * @since 2020-08-20
 */
public interface IFamilyManagerService {

    /**
     * 添加住户
     * @param familyManagerDTO
     */
    void addFamilyUser(FamilyManagerDTO familyManagerDTO);

    /**
     * 住户管理分页查询
     * @param familyManagerQryVO
     * @return
     */
    BasePageVO<FamilyManagerPageVO> page(FamilyManagerQryVO familyManagerQryVO);

    /**
     * 住户详情
     * @param userId
     * @return
     */
    FamilyManageDetailVO getDetailFamilyUser(Long id,Long familyId,String userId,Integer type);

    /**
     * 修改用户绑定
     * @param familyManagerDTO
     */
    void updateFamilyUser(FamilyManagerDTO familyManagerDTO);

    void deleteFamilyUser(Long id);

    /**
     * 获取成员类型
     * @return
     */
    List<SelectedIntegerVO> getFamilyUserTypes();
}
