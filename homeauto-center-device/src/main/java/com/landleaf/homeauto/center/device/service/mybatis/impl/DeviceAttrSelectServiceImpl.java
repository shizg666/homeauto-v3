package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.device.DeviceAttrSelect;
import com.landleaf.homeauto.center.device.model.mapper.DeviceAttrSelectMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IDeviceAttrSelectService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 协议属性具体值配置 服务实现类
 * </p>
 *
 * @author lokiy
 * @since 2021-01-04
 */
@Service
public class DeviceAttrSelectServiceImpl extends ServiceImpl<DeviceAttrSelectMapper, DeviceAttrSelect> implements IDeviceAttrSelectService {

    @Override
    public List<DeviceAttrSelect> getByAttribute(String attributeId) {
        QueryWrapper<DeviceAttrSelect> querWapper = new QueryWrapper<>();
        querWapper.eq("attr_id",attributeId);
        return list(querWapper);
    }
}
