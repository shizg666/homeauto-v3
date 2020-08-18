package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.ProductAttributeDO;
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

    @Select("select id from product_attribute where product_id = #{id}")
    List<String> getIdListByProductId(String id);
}
