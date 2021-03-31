package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeInfoDO;

import java.util.List;


/**
 * <p>
 * 产品属性值表 服务类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-15
 */
public interface IProductAttributeInfoService extends IService<ProductAttributeInfoDO> {

    /**
     * 通过属性ID和属性值code获取属性值信息
     *
     * @param attributeId 属性ID
     * @param code        属性值Code
     * @return 属性值信息
     */
    ProductAttributeInfoDO getProductAttributeInfoByAttrIdAndCode(String attributeId, String code);

    /**
     * 通过productAttributeId获取实体列表
     *
     * @param productAttributeId
     * @return
     */
    List<ProductAttributeInfoDO> listByProductAttributeId(String productAttributeId);

}
