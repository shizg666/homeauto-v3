package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneActionDO;
import com.landleaf.homeauto.common.domain.dto.sync.SyncSceneBO;
import com.landleaf.homeauto.common.domain.dto.sync.SyncSceneHvacAtionBO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 场景关联设备动作表 服务类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
public interface IFamilySceneActionService extends IService<FamilySceneActionDO> {

    /**
     * 通过sceneId获取实体列表
     *
     * @param sceneId 场景ID
     * @return 场景联动配置列表
     */
    List<FamilySceneActionDO> listBySceneId(String sceneId);

    /**
     * 通过设备序列号获取设备的属性
     *
     * @param deviceSn 设备序列号
     * @return 属性集合
     */
    Map<String, String> getDeviceActionAttributionOnMapByDeviceSn(String deviceSn);

    /**
     * 获取家庭场景的非暖通配置 ---- 场景同步
     *
     * @param familyId
     * @return
     */
    List<SyncSceneBO> getListSyncSceneDTO(String familyId);

    /**
     * 获取家庭场景的暖通配置 ---- 场景同步
     *
     * @param familyId
     * @return
     */
    List<SyncSceneHvacAtionBO> getListSceneHvacAction(String familyId);

    /**
     * 获取家庭场景的非暖通配置设备号集合 ---- 场景同步
     *
     * @param familyId
     * @return
     */
    List<String> getListDeviceSn(String familyId);
}
