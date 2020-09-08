package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoCategory;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.vo.product.ProductInfoSelectVO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.*;

import java.util.List;

/**
 * <p>
 * 产品表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
public interface IHomeAutoProductService extends IService<HomeAutoProduct> {

    HomeAutoProduct add(ProductDTO request);

    HomeAutoProduct update(ProductDTO request);

    BasePageVO<ProductPageVO> page(ProductQryDTO request);

    void delete(String id);

    /**
     * 获取协议下拉列表
     *
     * @return
     */
    List<SelectedVO> getProtocols(String categoryId);

    /**
     * 获取波特率下拉列表
     *
     * @return
     */
    List<SelectedIntegerVO> getBaudRates();

    /**
     * 获取校验模式下拉列表
     *
     * @return
     */
    List<SelectedIntegerVO> getCheckModes();

    /**
     * 修改产品 根据产品id获取产品属性信息
     *
     * @param id
     * @return
     */
    List<ProductAttributeBO> getListAttributeById(String id);

    /**
     * 根据产品id获取详情信息
     *
     * @param id
     * @return
     */
    ProductDetailVO getProductDetailInfo(String productId);


    /**
     * 获取性质类型
     *
     * @return
     */
    List<SelectedIntegerVO> getNatures();

    /**
     * 获取产品的品类信息
     * @param productCode
     * @return
     */
    HomeAutoCategory getCategoryByProductCode(String productCode);

    /**
     * 添加设备是 获取产品下拉列表
     * @return
     */
    List<ProductInfoSelectVO> getListProductSelect();

    /**
     * 获取故障类型下拉列表
     * @return
     */
    List<SelectedIntegerVO> getErrorTypes();

    /**
     * 获取某一产品只读属性下拉列表
     * @param productId
     * @return
     */
    List<SelectedVO> getReadAttrSelects(String productId);

}
