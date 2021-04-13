package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeDO;
import com.landleaf.homeauto.center.device.model.dto.product.ProductDTO;
import com.landleaf.homeauto.center.device.model.dto.product.ProductPageVO;
import com.landleaf.homeauto.center.device.model.vo.TotalCountBO;
import com.landleaf.homeauto.center.device.model.vo.product.ProductInfoSelectVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneDeviceAttributeVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.*;
import com.landleaf.homeauto.common.domain.vo.common.CascadeVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

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

    List<ProductAttributeBO> getListProductAttributeById(Long id);

    /**
     * 查看产品信息
     * @param id
     * @return
     */
    ProductDetailVO getProductDetailInfo(@Param("id") Long id);

//    List<ProductInfoSelectVO> getListProductSelect();

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

    /**
     * 根据产品id集合查询产品属性信息
     * @return
     */
    List<SceneDeviceAttributeVO> getListdeviceAttributeInfo(@Param("productIds") List<String> productIds);

    /**
     * 判断某一产品是否是暖通设备
     * @param productId
     * @return
     */
    @Select("select p.hvac_flag from home_auto_product p where p.id = #{productId}")
    int getHvacFlagById(@Param("productId") String productId);


    /**
     *
     * @return
     */
    List<CascadeVo> allProductType();

    /**
     * 获取产品绑定的协议品类信息
     * @param productId
     * @return
     */
    ProductProtocolInfoBO getProductProtocolInfo(@Param("productId") String productId);

    @Select("select p.category_code from home_auto_product p where p.id = #{productId}")
    String getCategoryCodeById(@Param("productId")String productId);

    @Select("select p.code from home_auto_product p where p.id = #{productId}")
    String getProductCodeById(@Param("productId")String productId);

    List<TotalCountBO> getCountGroupByCategory(@Param("categoryCodes") List<String> categoryCodes);

    /**
     * 获取品类下的最大的产品编码
     * @param categoryId
     * @return
     */
    @Select("select p.code from home_auto_product p where p.category_id  = #{categoryId} order by p.code desc limit 1")
    String getLastProductCodeByCategory(@Param("categoryId") Long categoryId);

    /**
     * 新增设备时获取品类下的产品下拉列表
     * @param categoryId
     * @return
     */
    List<ProductInfoSelectVO> getListProductSelectByCategoryId(String categoryId);

    /**
     * 判断产品下是否存在设备
     * @param productId
     * @return
     */
    @Select("select count(id) from house_template_device d where d.product_id = #{productId} limit 1")
    boolean getExistProductDevice(@Param("productId") Long productId);


    /**
     * 根据产品id和属性code获取 产品属性信息
     * @param productId
     * @param attrCode
     * @return
     */
    @Select("  SELECT pa.id,pa.type from product_attribute pa where pa.product_id = #{productId} and pa.code= #{attrCode} limit 1")
    ProductAttributeDO getProductAttr(@Param("productId")Long productId, @Param("attrCode")String attrCode);
}
