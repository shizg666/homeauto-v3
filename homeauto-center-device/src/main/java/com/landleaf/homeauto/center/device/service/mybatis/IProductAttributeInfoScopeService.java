package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeInfoScope;

import java.util.List;

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
     * 根据产品属性值id获取取值范围
     *
     * @param productAttributeId 产品属性id
     * @param attributeValue     属性值
     * @return
     */
    ProductAttributeInfoScope getByProductAttributeId(String productAttributeId, String attributeValue);

}
