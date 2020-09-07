package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.vo.product.ProductInfoSelectVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

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

    List<ProductInfoSelectVO> getListProductSelect();

    List<ProductDetailVO> getListProductDetailInfo(@Param("ids") List<String> ids);

    /**
     * 获取某一产品只读属性下拉列表
     */
    @Select("  select pa.code as value,pa.name as label from product_attribute pa where pa.nature = 2 and pa.product_id = #{productId}")
    List<SelectedVO> getReadAttrSelects(@Param("productId") String productId);

    /**
     * 获取产品故障详情信息
     * @param productId
     * @return
     */
    List<ProductAttributeErrorVO> getListAttributesErrorsDeatil(@Param("productId") String productId);
}
