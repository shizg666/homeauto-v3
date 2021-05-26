package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeInfoScope;
import com.landleaf.homeauto.center.device.model.domain.sys_product.SysProductAttributeInfoScope;
import com.landleaf.homeauto.center.device.model.smart.bo.ProductAttributeValueScopeBO;
import com.landleaf.homeauto.common.domain.vo.category.ProductAttributeScopeDTO;

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
