package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneHvacConfig;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/9/2
 */
public interface IFamilySceneHvacConfigService extends IService<FamilySceneHvacConfig> {

    /**
     * 根据设备号和家庭id查询改设备关联的场景暖通配置id集合
     *
     * @param deviceSn
     * @param familyId
     * @return
     */
    List<String> getListIds(String deviceSn, String familyId);

    /**
     * 通过 sceneId 查询实体列表
     *
     * @param sceneId
     * @return
     */
    List<FamilySceneHvacConfig> listBySceneId(String sceneId);
}
