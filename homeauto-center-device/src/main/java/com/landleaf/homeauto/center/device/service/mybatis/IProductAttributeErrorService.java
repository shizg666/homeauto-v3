package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeError;
import com.landleaf.homeauto.common.domain.vo.category.AttributeErrorDTO;
import com.landleaf.homeauto.common.domain.vo.category.AttributeErrorQryDTO;
import com.landleaf.homeauto.common.domain.vo.category.ProductAttributeErrorVO;
import com.landleaf.homeauto.common.domain.vo.category.ProductErrorAttributeDTO;

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

    void add(ProductErrorAttributeDTO request);

    void update(ProductErrorAttributeDTO request);

    /**
     * 查看产品故障属性列表
     * @param productId
     * @return
     */
    /**
     * 产品查看详情之故障详情页
     * @param productId
     * @return
     */
    List<ProductAttributeErrorVO> getListAttributesErrorsDeatil(String productId);
}
