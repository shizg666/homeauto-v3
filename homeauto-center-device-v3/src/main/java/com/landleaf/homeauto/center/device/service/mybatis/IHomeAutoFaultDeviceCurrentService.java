package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.status.HomeAutoFaultDeviceCurrent;

import java.util.List;

/**
 * <p>
 * 设备当前故障值 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2021-06-01
 */
public interface IHomeAutoFaultDeviceCurrentService extends IService<HomeAutoFaultDeviceCurrent> {

    List<HomeAutoFaultDeviceCurrent> getCurrentByDevice(Long familyId, Long deviceId);

    void storeOrUpdateCurrentFaultValue(HomeAutoFaultDeviceCurrent data);

    void removeCurrentFaultValue(Long familyId, Long deviceId, String code, int type);

    long countCurrentFault(Long familyId, Long deviceId, int type);
}
