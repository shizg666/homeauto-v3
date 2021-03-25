package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.protocol.ProtocolAttrSelect;
import com.landleaf.homeauto.center.device.model.dto.protocol.ProtocolAttrSelectDTO;

import java.util.List;

/**
 * <p>
 * 协议属性具体值配置 服务类
 * </p>
 *
 * @author lokiy
 * @since 2020-12-25
 */
public interface IProtocolAttrSelectService extends IService<ProtocolAttrSelect> {

    /**
     * 新增协议属性可选值
     * @param protocolAttrDetail
     */
    void add(List<ProtocolAttrSelectDTO> protocolAttrDetail);
}
