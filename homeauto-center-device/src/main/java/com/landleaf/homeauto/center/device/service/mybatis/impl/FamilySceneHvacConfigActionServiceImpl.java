package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.bo.HvacSceneConfigActionBO;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneHvacConfigAction;
import com.landleaf.homeauto.center.device.model.mapper.FamilySceneHvacConfigActionMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneHvacConfigActionService;
import org.springframework.stereotype.Service;

/**
 * @author Yujiumin
 * @version 2020/9/2
 */
@Service
public class FamilySceneHvacConfigActionServiceImpl extends ServiceImpl<FamilySceneHvacConfigActionMapper, FamilySceneHvacConfigAction> implements IFamilySceneHvacConfigActionService {

    @Override
    public HvacSceneConfigActionBO getHvacSceneConfigAction(String sceneId) {
        return null;
    }

}
