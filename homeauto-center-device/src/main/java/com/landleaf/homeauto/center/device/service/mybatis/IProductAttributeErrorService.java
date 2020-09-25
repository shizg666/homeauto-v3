package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeError;
import com.landleaf.homeauto.common.domain.vo.category.*;

import java.util.List;

/**
 * <p>
 * 产品故障属性表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-03
 */
public interface IProductAttributeErrorService extends IService<ProductAttributeError> {

    /**
     * 根据产品获取产品故障属性id集合
     * @param id
     * @return
     */
    List<String> getIdListByProductId(String id);

    /**
     * 根据产品属性code和产品code获取相应的故障信息，没有返回null
     * @param id
     * @return
     */
    List<String> getErrorInfo(String id);

    /**
     * 获取故障属性信息
     * @param request
     * @return
     */
    AttributeErrorDTO getErrorAttributeInfo(AttributeErrorQryDTO request);

    void add(ProductAttributeErrorDTO request);

    void update(ProductAttributeErrorDTO request);


    /**
     * 产品查看详情之故障详情页
     * @param productId
     * @return
     */
    List<ProductAttributeErrorVO> getListAttributesErrorsDeatil(String productId);

    /**
     * 删除产品故障属性
     * @param attrId
     */
    void deleteErrorAttrById(String attrId);

    /**
     * 查询产品属性精度(code 为null，查产品所有属性)
     * @param request
     * @return
     */
    List<AttributePrecisionDTO> getAttributePrecision(AttributePrecisionQryDTO request);

    /**
     * 产品故障缓存--查询所有产品故障信息
     * @return
     */
    List<AttributeErrorDTO> getListCacheInfo();

    /**
     * 缓存产品属性精度信息
     * @param data
     * @param productCode
     */
    void saveCachePrecision(List<AttributePrecisionDTO> data , String productCode);
}
