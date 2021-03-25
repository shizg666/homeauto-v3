package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.protocol.ProtocolAttrSelect;
import com.landleaf.homeauto.center.device.model.dto.protocol.ProtocolAttrSelectDTO;
import com.landleaf.homeauto.center.device.model.mapper.ProtocolAttrSelectMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IProtocolAttrSelectService;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 协议属性具体值配置 服务实现类
 * </p>
 *
 * @author lokiy
 * @since 2020-12-25
 */
@Service
public class ProtocolAttrSelectServiceImpl extends ServiceImpl<ProtocolAttrSelectMapper, ProtocolAttrSelect> implements IProtocolAttrSelectService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(List<ProtocolAttrSelectDTO> protocolAttrDetail) {
        List<ProtocolAttrSelect> details = BeanUtil.mapperList(protocolAttrDetail, ProtocolAttrSelect.class);
        saveBatch(details);
    }
}
