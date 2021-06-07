package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.screenapk.ProjectScreenUpgrade;
import com.landleaf.homeauto.center.device.model.dto.screenapk.*;
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
public interface IProjectScreenUpgradeService extends IService<ProjectScreenUpgrade> {

    void saveUpgrade(ProjectScreenUpgradeSaveDTO requestBody);

    void updateUpgrade(ProjectScreenUpgradeUpdateDTO requestBody);

    BasePageVO<ProjectScreenUpgradeInfoDetailDTO> getInfoDetail(ProjectScreenUpgradeDetailPageDTO upgradeId);

    BasePageVO<ProjectScreenUpgradeInfoDTO> pageList(ProjectScreenUpgradePageDTO requestBody);

    void upgrade(Long detailId);
}
