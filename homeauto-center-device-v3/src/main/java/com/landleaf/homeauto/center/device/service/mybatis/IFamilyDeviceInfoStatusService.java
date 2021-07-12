package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.status.FamilyDeviceInfoStatus;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 设备基本状态表(暖通、数值、在线离线标记) 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2021-06-01
 */
public interface IFamilyDeviceInfoStatusService extends IService<FamilyDeviceInfoStatus> {

    FamilyDeviceInfoStatus getFamilyDeviceInfoStatus(Long familyId, Long deviceId);

    boolean storeOrUpdateDeviceInfoStatus(FamilyDeviceInfoStatus familyDeviceInfoStatus, int type);

    void updateOnLineFlagByFamily(Long familyId, Integer status);

    /**
     * 获取家庭下的故障设备信息
     * @param familyIds
     * @return
     */
    List<FamilyDeviceInfoStatus> getListStatistic(List<Long> familyIds);

    /**
     * 获取当前设备在线数
     * @return 根据品类区分当前设备在线数
     */
    Map<String, Long> getOnlineCountMap();

}
