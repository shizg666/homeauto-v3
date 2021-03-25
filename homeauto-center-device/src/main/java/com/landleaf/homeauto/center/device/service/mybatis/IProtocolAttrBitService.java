package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.protocol.ProtocolAttrBit;
import com.landleaf.homeauto.center.device.model.dto.protocol.ProtocolAttrBitDTO;

import java.util.List;

/**
 * <p>
 * 协议属性二进制值配置 服务类
 * </p>
 *
 * @author lokiy
 * @since 2020-12-25
 */
public interface IProtocolAttrBitService extends IService<ProtocolAttrBit> {

    /**
     *
     * @param protocolAttrBitDTO
     */
    void add(List<ProtocolAttrBitDTO> protocolAttrBitDTO);
}
