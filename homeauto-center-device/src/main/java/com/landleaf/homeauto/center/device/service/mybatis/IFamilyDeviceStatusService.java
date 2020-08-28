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
     * 通过设备序列号获取设备属性
     *
     * @param deviceSn 设备序列号
     * @return 设备属性集合
     */
    List<FamilyDeviceStatusDO> getDeviceAttributionsBySn(String deviceSn);

    /**
     * 批量插入设备状态
     *
     * @param deviceStatusBOList
     */
    void insertBatchDeviceStatus(List<DeviceStatusBO> deviceStatusBOList);

}
