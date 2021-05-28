package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeDO;
import com.landleaf.homeauto.center.device.model.smart.bo.ProductAttributeBO;
import com.landleaf.homeauto.center.device.model.vo.product.ProductAttrInfoBO;

import java.util.List;


/**
 * <p>
 * 产品属性信息表 服务类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-15
 */
public interface IProductAttributeService extends IService<ProductAttributeDO> {

    /**
     * 通过ID获取产品属性
     *
     * @param id
     * @return
     */
    ProductAttributeDO getProductAttributeById(String id);

    /**
     * 根据产品id查询属性id集合
     *
     * @param id
     * @return
     */
    List<Long> getIdListByProductId(Long id);

    /**
     * 通过productCode获取实体列表
     *
     * @param productCode 产品编码
     * @return 实体列表
     */
    List<ProductAttributeBO> listByProductCode(String productCode);
    /**
     * 通过productCode获取实体列表
     *
     * @param productCode 产品编码
     * @return 实体列表
     */
    List<ProductAttributeDO> getByProductCode(String productCode);


    /**
     * 获取某一品类下所有产品的属性和属性值
     * @param categoryCode
     * @return
     */
    List<ProductAttrInfoBO> getAttributeAndValByCategoryCode(String categoryCode);
}
