package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.device.DeviceAttrPrecision;
import com.landleaf.homeauto.center.device.model.mapper.DeviceAttrPrecisionMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IDeviceAttrPrecisionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 协议属性精度配置 服务实现类
 * </p>
 *
 * @author lokiy
 * @since 2021-01-04
 */
@Service
public class DeviceAttrPrecisionServiceImpl extends ServiceImpl<DeviceAttrPrecisionMapper, DeviceAttrPrecision> implements IDeviceAttrPrecisionService {

    @Override
    public DeviceAttrPrecision getByAttribute(String attributeId) {
        QueryWrapper<DeviceAttrPrecision> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("attr_id",attributeId);
        List<DeviceAttrPrecision> list = list(queryWrapper);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }
        return null;
    }
}
