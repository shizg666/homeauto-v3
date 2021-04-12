package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeError;
import com.landleaf.homeauto.common.domain.vo.category.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 产品故障属性表 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-03
 */
public interface ProductAttributeErrorMapper extends BaseMapper<ProductAttributeError> {

    @Select("select id from product_attribute_error where product_id = #{id}")
    List<Long> getIdListByProductId(@Param("id") Long id);


    AttributeErrorDTO getErrorAttributeInfo(AttributeErrorQryDTO request);

    /**
     * 获取产品故障详情信息
     * @param productId
     * @return
     */
    List<ProductAttributeErrorVO> getListAttributesErrorsDeatil(@Param("productId") Long productId);

    int existErrorAttrCode(@Param("code") String code, @Param("productId") String productId);

    /**
     * 查询产品属性精度(code 为null，查产品所有属性)
     * @param request
     * @return
     */
    List<AttributePrecisionDTO> getAttributePrecision(AttributePrecisionQryDTO request);

    /**
     * 产品故障缓存--查询所有产品故障信息
     * @return
     */
    List<AttributeErrorDTO> getListCacheInfo();
}
