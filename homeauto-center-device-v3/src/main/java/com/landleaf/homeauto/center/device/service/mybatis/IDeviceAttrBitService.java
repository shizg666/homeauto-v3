package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.device.DeviceAttrBit;

import java.util.List;

/**
 * <p>
 * 协议属性二进制值配置 服务类
 * </p>
 *
 * @author lokiy
 * @since 2021-01-04
 */
public interface IDeviceAttrBitService extends IService<DeviceAttrBit> {

    /**
     * 获取故障属性的结合
     * @param id
     * @return
     */
    List<DeviceAttrBit> getListByAttrId(String attrId);
}
