package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.protocol.ProtocolAttrPrecision;
import com.landleaf.homeauto.center.device.model.dto.protocol.ProtocolAttrPrecisionDTO;
import com.landleaf.homeauto.center.device.model.mapper.ProtocolAttrPrecisionMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IProtocolAttrPrecisionService;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 协议属性精度配置 服务实现类
 * </p>
 *
 * @author lokiy
 * @since 2020-12-25
 */
@Service
public class ProtocolAttrPrecisionServiceImpl extends ServiceImpl<ProtocolAttrPrecisionMapper, ProtocolAttrPrecision> implements IProtocolAttrPrecisionService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ProtocolAttrPrecisionDTO protocolAttrPrecision) {
        ProtocolAttrPrecision precision = BeanUtil.mapperBean(protocolAttrPrecision,ProtocolAttrPrecision.class);
        save(precision);
    }
}
