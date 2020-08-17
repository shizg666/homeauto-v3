package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceStatusDO;
import com.landleaf.homeauto.center.device.model.vo.AttributionVO;

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
     * 通过设备ID获取设备属性
     *
     * @param deviceId 设备ID
     * @return 设备属性集合
     */
    List<AttributionVO> getDeviceAttributionsById(String deviceId);

}
