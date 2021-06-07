package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.sysproduct.SysProductAttributeInfoScope;

import java.util.List;

/**
 * <p>
 * 产品属性精度范围表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
public interface ISysProductAttributeInfoScopeService extends IService<SysProductAttributeInfoScope> {


    List<SysProductAttributeInfoScope> getByProductCode(String productCode);
}
