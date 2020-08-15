package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.model.po.device.ProductAttributeInfoPO;


/**
 * <p>
 * 产品属性值表 服务类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-15
 */
public interface IProductAttributeInfoService extends IService<ProductAttributeInfoPO> {

    /**
     * 通过属性ID和属性值code获取属性值信息
     *
     * @param attributeId 属性ID
     * @param code        属性值Code
     * @return 属性值信息
     */
    ProductAttributeInfoPO getProductAttributeInfoByAttrIdAndCode(String attributeId, String code);

}
