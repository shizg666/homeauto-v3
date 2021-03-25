package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.device.DeviceAttrPrecision;

/**
 * <p>
 * 协议属性精度配置 服务类
 * </p>
 *
 * @author lokiy
 * @since 2021-01-04
 */
public interface IDeviceAttrPrecisionService extends IService<DeviceAttrPrecision> {

    DeviceAttrPrecision getByAttribute(String attributeId);
}
