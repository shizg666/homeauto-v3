package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneHvacConfigActionPanel;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/9/2
 */
public interface IFamilySceneHvacConfigActionPanelService extends IService<FamilySceneHvacConfigActionPanel> {

    /**
     * 获取家庭场景配置---面板动作 ---- 场景同步
     * @param familyId
     * @return
     */
    List<FamilySceneHvacConfigActionPanel> getListSyncPanelAction(String familyId);
}
