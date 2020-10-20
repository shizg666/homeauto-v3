package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneHvacConfigAction;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/9/2
 */
public interface IFamilySceneHvacConfigActionService extends IService<FamilySceneHvacConfigAction> {


    /**
     * 根据暖通配置id集合 查询暖通动作id集合
     * @param hvacConfigIds
     * @return
     */
    List<String> getListIds(List<String> hvacConfigIds);
}
