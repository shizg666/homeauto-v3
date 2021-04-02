package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeInfoScope;
import com.landleaf.homeauto.center.device.model.smart.bo.ProductAttributeValueScopeBO;

/**
 * <p>
 * 产品属性精度范围表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
public interface IProductAttributeInfoScopeService extends IService<ProductAttributeInfoScope> {

    /**
     * 根据productAttributeId获取取值范围
     *
     * @param parentId 产品属性id|产品属性值ID
     * @return
     */
    ProductAttributeValueScopeBO getByProductAttributeId(String parentId);

}
