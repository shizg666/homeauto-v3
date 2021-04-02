package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.device.DeviceAttrSelect;

import java.util.List;

/**
 * <p>
 * 协议属性具体值配置 服务类
 * </p>
 *
 * @author lokiy
 * @since 2021-01-04
 */
public interface IDeviceAttrSelectService extends IService<DeviceAttrSelect> {

    /**
     *  根据属性获取枚举值
     * @param attributeId  属性ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.domain.device.DeviceAttrSelect>
     * @author wenyilu
     * @date 2021/1/29 11:52
     */
    List<DeviceAttrSelect> getByAttribute(String attributeId);
}
