package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonSceneDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.FamilyScene;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.HouseTemplateScene;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilySceneBO;
import com.landleaf.homeauto.center.device.model.vo.scene.house.*;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;

import java.util.List;

/**
 * <p>
 * 户型情景表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-28
 */
public interface IFamilySceneService extends IService<FamilyScene> {


    /**
     * 获取家庭场景
     * @param familyId
     * @return
     */
    List<FamilyScene> getListSceneByfId(Long familyId);
}
