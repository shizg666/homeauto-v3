package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.protocol.ProtocolAttrBit;
import com.landleaf.homeauto.center.device.model.dto.protocol.ProtocolAttrBitDTO;
import com.landleaf.homeauto.center.device.model.mapper.ProtocolAttrBitMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IProtocolAttrBitService;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 协议属性二进制值配置 服务实现类
 * </p>
 *
 * @author lokiy
 * @since 2020-12-25
 */
@Service
public class ProtocolAttrBitServiceImpl extends ServiceImpl<ProtocolAttrBitMapper, ProtocolAttrBit> implements IProtocolAttrBitService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(List<ProtocolAttrBitDTO> protocolAttrBitDTO) {
        List<ProtocolAttrBit> bits = BeanUtil.mapperList(protocolAttrBitDTO,ProtocolAttrBit.class);
        saveBatch(bits);
    }
}
