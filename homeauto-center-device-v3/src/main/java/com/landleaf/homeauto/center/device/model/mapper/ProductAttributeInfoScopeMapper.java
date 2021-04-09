package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeInfoScope;
import com.landleaf.homeauto.common.domain.vo.category.ProductAttributeScopeDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 产品属性精度范围表 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
public interface ProductAttributeInfoScopeMapper extends BaseMapper<ProductAttributeInfoScope> {

    /**
     *根据属性id 获取属性值域的配置信息
     * @param attrId
     * @return
     */
    @Select("SELECT pas.max,pas.min,pas.step,pas.precision from product_attribute_info_scope pas where pas.parent_id = #{attrId} limit 1")
    ProductAttributeScopeDTO getAttrScopeByAttrId(@Param("attrId")Long attrId);
}
