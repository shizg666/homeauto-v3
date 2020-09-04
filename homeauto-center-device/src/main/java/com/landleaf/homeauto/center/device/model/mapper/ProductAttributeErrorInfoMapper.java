package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeErrorInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-03
 */
public interface ProductAttributeErrorInfoMapper extends BaseMapper<ProductAttributeErrorInfo> {


    @Select("SELECT pei.desc FROM product_attribute_error_info pei where pei.error_attribute_id = #{errorAttributeId} ORDER BY sort_no asc")
    List<String> getListDesc(@Param("errorAttributeId")String errorAttributeId);
}
