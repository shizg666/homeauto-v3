package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeErrorInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-03
 */
public interface IProductAttributeErrorInfoService extends IService<ProductAttributeErrorInfo> {

    @Select("SELECT pei.desc FROM product_attribute_error_info pei where pei.error_attribute_id = #{errorAttributeId} ORDER BY sort_no asc")
    List<String> getListDesc(@Param("errorAttributeId") String errorAttributeId);
}
