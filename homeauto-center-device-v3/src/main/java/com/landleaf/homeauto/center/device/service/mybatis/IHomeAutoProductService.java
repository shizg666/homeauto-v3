package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.constant.DeviceNatureEnum;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.dto.product.ProductDTO;
import com.landleaf.homeauto.center.device.model.dto.product.ProductPageVO;
import com.landleaf.homeauto.center.device.model.vo.product.ProductInfoSelectVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneDeviceAttributeVO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedLongVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.*;
import com.landleaf.homeauto.common.domain.vo.common.CascadeVo;

import java.util.List;
import java.util.Map;

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

    /**
     * 产品分页查询
     * @param request
     * @return
     */
    BasePageVO<ProductPageVO> page(ProductQryDTO request);

    void delete(Long id);

    /**
     * 获取协议下拉列表
     *
     * @return
     */
    List<SelectedVO> getProtocols();
//
//    /**
//     * 获取波特率下拉列表
//     *
//     * @return
//     */
//    List<SelectedIntegerVO> getBaudRates();
//
//    /**
//     * 获取校验模式下拉列表
//     *
//     * @return
//     */
//    List<SelectedIntegerVO> getCheckModes();

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
     * @param productId
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
     *
     * @param productCode
     * @return
     */
    HomeAutoProduct getCategoryByProductCode(String productCode);

    /**
     * 添加设备是 获取产品下拉列表
     *
     * @return
     */
    List<SelectedLongVO> getListProductSelect();

    /**
     * 获取故障类型下拉列表
     *
     * @return
     */
    List<SelectedIntegerVO> getErrorTypes();

    /**
     * 获取某一产品只读属性下拉列表
     *
     * @param productId
     * @return
     */
    List<SelectedVO> getReadAttrSelects(String productId);

    /**
     * 根据产品id集合查询产品属性信息(去除只读属性)
     *
     * @param productIds
     * @return
     */
    List<SceneDeviceAttributeVO> getListdeviceAttributeInfo(List<String> productIds);





    /**
     * 判断某一产品是否是暖通设备
     *
     * @param productId
     * @return
     */
    boolean getHvacFlagById(String productId);

    /**
     * 三级联动-所有产品类别
     *
     * @return
     */
    List<CascadeVo> allProductType();

    /**
     * 产品下拉选择value code
     *
     * @return
     */
    List<SelectedVO> getListCodeSelects();


    /**
     * 根据nature获取实体列表
     *
     * @param nature 产品性质
     * @return 实体列表
     */
    List<HomeAutoProduct> listProductByNature(DeviceNatureEnum nature);

    /**
     * 获取产品关联的协议属性列表
     * @param productId
     * @return
     */
    ProductProtocolInfoBO getProductProtocolInfo(String productId);


    /**
     * 获取产品品类code
     * @param productId
     * @return
     */
    String getCategoryCodeById(String productId);

    /**
     * 获取产品code
     * @param productId
     * @return
     */
    String getProductCodeById(String productId);

    /**
     * 按品类统计产品数量
     * @return
     */
    Map<String, Integer> getCountGroupByCategory(List<String> categoryCodes);


    /**
     * 新增设备时获取品类下的产品下拉列表
     * @param categoryId
     * @return
     */
    List<ProductInfoSelectVO> getListProductSelectByCategoryId(String categoryId);

    /**
     * 判断产品下是否有设备
     */
    boolean getExistProductDevice(Long productId);
}
