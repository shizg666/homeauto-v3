package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeDO;
import com.landleaf.homeauto.center.device.model.smart.bo.ProductAttributeBO;
import com.landleaf.homeauto.center.device.model.vo.product.ProductAttrInfoBO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * <p>
 * 产品属性信息表 Mapper 接口
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-15
 */
public interface ProductAttributeMapper extends BaseMapper<ProductAttributeDO> {

    @Select("select id from product_attribute where product_id = #{productId}")
    List<Long> getIdListByProductId(@Param("productId") Long productId);

    /**
     * 获取某一品类下所有产品的属性和属性值
     * @param categoryCode
     * @return
     */
    List<ProductAttrInfoBO> getAttributeAndValByCategoryCode(@Param("categoryCode") String categoryCode);

    /**
     * 获取品类下所有产品的属性和属性值
     * @param categoryCodes
     * @return
     */
    List<ProductAttrInfoBO> getAttributeAndValByCategoryCodes(@Param("categoryCodes") List<String> categoryCodes);
}
