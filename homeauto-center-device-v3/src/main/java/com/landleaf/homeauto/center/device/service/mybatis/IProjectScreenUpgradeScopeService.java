package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.screenapk.ProjectScreenUpgrade;
import com.landleaf.homeauto.center.device.model.domain.screenapk.ProjectScreenUpgradeScope;
import com.landleaf.homeauto.center.device.model.dto.screenapk.ProjectScreenUpgradeSaveDTO;
import com.landleaf.homeauto.center.device.model.dto.screenapk.ProjectScreenUpgradeUpdateDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wenyilu
 * @since 2021-06-07
 */
public interface IProjectScreenUpgradeScopeService extends IService<ProjectScreenUpgradeScope> {

    void saveUpgradeScope(ProjectScreenUpgrade saveData, ProjectScreenUpgradeSaveDTO requestBody);

    void updateUpgradeScope(ProjectScreenUpgrade screenUpgrade, ProjectScreenUpgradeUpdateDTO requestBody);
}
