package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.sysproduct.SysProductAttributeInfoScope;
import com.landleaf.homeauto.center.device.model.mapper.SysProductAttributeInfoScopeMapper;
import com.landleaf.homeauto.center.device.service.mybatis.ISysProductAttributeInfoScopeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 产品属性精度范围表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
@Slf4j
@Service
public class SysProductAttributeInfoScopeServiceImpl extends ServiceImpl<SysProductAttributeInfoScopeMapper, SysProductAttributeInfoScope> implements ISysProductAttributeInfoScopeService {


    @Override
    public List<SysProductAttributeInfoScope> getByProductCode(String productCode) {
        LambdaQueryWrapper<SysProductAttributeInfoScope> queryWrapper = new LambdaQueryWrapper<SysProductAttributeInfoScope>().eq(SysProductAttributeInfoScope::getSysProductCode,productCode);
        return list(queryWrapper);
    }
}
