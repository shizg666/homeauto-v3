package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.status.FamilyDeviceInfoStatus;

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
}
