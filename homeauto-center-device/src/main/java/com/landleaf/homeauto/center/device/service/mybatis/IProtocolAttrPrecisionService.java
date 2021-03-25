package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.protocol.ProtocolAttrPrecision;
import com.landleaf.homeauto.center.device.model.dto.protocol.ProtocolAttrPrecisionDTO;

/**
 * <p>
 * 协议属性精度配置 服务类
 * </p>
 *
 * @author lokiy
 * @since 2020-12-25
 */
public interface IProtocolAttrPrecisionService extends IService<ProtocolAttrPrecision> {

    /**
     *
     * @param protocolAttrPrecision
     */
    void add(ProtocolAttrPrecisionDTO protocolAttrPrecision);
}
