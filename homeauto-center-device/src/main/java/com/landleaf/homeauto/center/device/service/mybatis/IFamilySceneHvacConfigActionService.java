package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.bo.HvacSceneConfigActionBO;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneHvacConfigAction;

/**
 * @author Yujiumin
 * @version 2020/9/2
 */
public interface IFamilySceneHvacConfigActionService extends IService<FamilySceneHvacConfigAction> {

    /**
     * 获取暖通场景工作模式
     *
     * @param sceneId
     * @return
     */
    HvacSceneConfigActionBO getHvacSceneConfigAction(String sceneId);


}
