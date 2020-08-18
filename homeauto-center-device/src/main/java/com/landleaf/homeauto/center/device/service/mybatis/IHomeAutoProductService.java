package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.ProductAttributeVO;
import com.landleaf.homeauto.common.domain.vo.category.ProductDTO;
import com.landleaf.homeauto.common.domain.vo.category.ProductPageVO;
import com.landleaf.homeauto.common.domain.vo.category.ProductQryDTO;

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

    void add(ProductDTO request);

    void update(ProductDTO request);

    BasePageVO<ProductPageVO> page(ProductQryDTO request);

    void delete(String id);

    /**
     * 获取协议下拉列表
     * @return
     */
    List<SelectedVO> getProtocols();
    /**
     * 获取波特率下拉列表
     * @return
     */
    List<SelectedIntegerVO> getBaudRates();
    /**
     * 获取校验模式下拉列表
     * @return
     */
    List<SelectedIntegerVO> getCheckModes();

    /**
     * 修改产品 根据产品id获取产品属性信息
     * @param id
     * @return
     */
    List<ProductAttributeVO> getListAttributeById(String id);
}
