package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.mapper.ProductAttributeInfoMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeInfoService;
import com.landleaf.homeauto.model.po.device.ProductAttributeInfoPO;
import com.landleaf.homeauto.model.po.device.ProductAttributePO;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品属性值表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-15
 */
@Service
public class ProductAttributeInfoServiceImpl extends ServiceImpl<ProductAttributeInfoMapper, ProductAttributeInfoPO> implements IProductAttributeInfoService {

    @Override
    public ProductAttributeInfoPO getProductAttributeInfoByAttrIdAndCode(String attributeId, String code) {
        QueryWrapper<ProductAttributePO> productAttributeQueryWrapper = new QueryWrapper<>();
        productAttributeQueryWrapper.eq("product_attribute_id", attributeId);
        productAttributeQueryWrapper.eq("code", code);

        return null;
    }
}
