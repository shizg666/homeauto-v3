package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonSceneDO;

import java.util.List;

/**
 * <p>
 * 家庭常用场景表 服务类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
public interface IFamilyCommonSceneService extends IService<FamilyCommonSceneDO> {

    /**
     * 通过家庭ID获取常用场景
     *
     * @param familyId 家庭ID
     * @return 常用场景
     */
    List<String> getCommonSceneIdListByFamilyId(String familyId);

    /**
     * 删除家庭常用场景
     *
     * @param familyId
     * @param sceneId
     */
    void deleteFamilySceneCommonScene(String familyId, String sceneId);

    /**
     * 场景是否存在
     *
     * @param familyId
     * @param sceneId
     * @return
     */
    boolean isExist(String familyId, String sceneId);

    /**
     * 获取通过familyId获取家庭常用场景
     *
     * @param familyId 家庭ID
     * @return 常用场景列表信息
     */
    List<FamilyCommonSceneDO> listFamilyCommonSceneEntityByFamilyId(String familyId);

}
