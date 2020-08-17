package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.model.po.device.ProductAttributePO;


/**
 * <p>
 * 产品属性信息表 服务类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-15
 */
public interface IProductAttributeService extends IService<ProductAttributePO> {

    /**
     * 通过ID获取产品属性
     *
     * @param id
     * @return
     */
    ProductAttributePO getProductAttributeById(String id);

}
