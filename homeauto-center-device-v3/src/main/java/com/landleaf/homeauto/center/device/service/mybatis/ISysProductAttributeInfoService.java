package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.sysproduct.SysProductAttributeInfo;

import java.util.List;

/**
 * <p>
 * 系统产品属性值表 服务类
 * </p>
 *
 * @author lokiy
 * @since 2021-05-24
 */
public interface ISysProductAttributeInfoService extends IService<SysProductAttributeInfo> {

    List<SysProductAttributeInfo> getByProductCode(String productCode);
}
