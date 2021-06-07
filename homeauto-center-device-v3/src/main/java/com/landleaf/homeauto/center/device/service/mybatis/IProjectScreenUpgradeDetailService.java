package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.screenapk.ProjectScreenUpgrade;
import com.landleaf.homeauto.center.device.model.domain.screenapk.ProjectScreenUpgradeDetail;
import com.landleaf.homeauto.center.device.model.domain.screenapk.ProjectScreenUpgradeScope;
import com.landleaf.homeauto.center.device.model.dto.screenapk.ProjectScreenUpgradeDetailPageDTO;
import com.landleaf.homeauto.center.device.model.dto.screenapk.ProjectScreenUpgradeInfoDetailDTO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wenyilu
 * @since 2021-06-07
 */
public interface IProjectScreenUpgradeDetailService extends IService<ProjectScreenUpgradeDetail> {

    List<ProjectScreenUpgradeDetail> listRecordsByUpgradeId(Long upgradeId);

    /**
     * 修改ota升级记录时，同步对推送记录详情表进行操作（新增、删除）
     * @param upgradeId
     * @param projectFamily
     * @param existScopes
     * @param updatePaths
     */
    void updateDetails4UpdateScope(ProjectScreenUpgrade upgradeId, List<HomeAutoFamilyDO> projectFamily, List<ProjectScreenUpgradeScope> existScopes, List<String> updatePaths);

    Integer countByUpgradeIdAndStatus(Long upgradeId, Integer type);

    BasePageVO<ProjectScreenUpgradeInfoDetailDTO> pageByCondition(ProjectScreenUpgradeDetailPageDTO requestDTO);
}
