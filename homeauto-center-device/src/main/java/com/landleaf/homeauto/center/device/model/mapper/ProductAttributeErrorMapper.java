package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeError;
import com.landleaf.homeauto.common.domain.vo.category.AttributeErrorDTO;
import com.landleaf.homeauto.common.domain.vo.category.AttributeErrorQryDTO;
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
    List<String> getIdListByProductId(@Param("id") String id);


    AttributeErrorDTO getErrorAttributeInfo(AttributeErrorQryDTO request);
}
