package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.common.domain.vo.category.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 产品表 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
public interface HomeAutoProductMapper extends BaseMapper<HomeAutoProduct> {

    int existCheck(ProductDTO request);

    List<ProductPageVO> listPage(ProductQryDTO request);

    List<ProductAttributeBO> getListProductAttributeById(String id);

    ProductDetailVO getProductDetailInfo(@Param("id") String id);
}
