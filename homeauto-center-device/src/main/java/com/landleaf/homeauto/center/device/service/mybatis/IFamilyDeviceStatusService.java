package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.bo.DeviceStatusBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceStatusDO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceAttributionVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-15
 */
public interface IFamilyDeviceStatusService extends IService<FamilyDeviceStatusDO> {

    /**
     * 获取设备的属性
     *
     * @param deviceId 设备ID
     * @return 设备属性集合
     */
    List<String> getDeviceAttributionsById(String deviceId);

    /**
     * 批量插入设备状态
     *
     * @param deviceStatusBOList 设备状态信息
     */
    void insertBatchDeviceStatus(List<DeviceStatusBO> deviceStatusBOList);

}
