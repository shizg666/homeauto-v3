package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrCategoryBO;
import com.landleaf.homeauto.center.device.model.domain.sys_product.SysProduct;
import com.landleaf.homeauto.center.device.model.vo.sys_product.*;

import java.util.List;

/**
 * <p>
 * 系统产品表 服务类
 * </p>
 *
 * @author lokiy
 * @since 2021-05-24
 */
public interface ISysProductService extends IService<SysProduct> {

    /**
     * 新增系统产品
     * @param requestDTO
     */
    void addSysProduct(SysProductDTO requestDTO);

    /**
     * 修改系统产品
     * @param requestDTO
     */
    void updateSysProdut(SysProductDTO requestDTO);

    /**
     * 删除系统产品
     * @param sysProductId
     */
    void deleteSysProdutById(Long sysProductId);

    /**
     * 查看系统产品详情
     * @param sysProductId
     */
    SysProductDetailVO getDetailSysProdut(Long sysProductId);
    /**
     * @param: productCode
     * @description: 获取系统产品属性
     * @return: java.util.List<com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrCategoryBO>
     * @author: wyl
     * @date: 2021/5/26
     */
    List<ScreenProductAttrCategoryBO> getAllAttrByCode(String productCode);

    /**
     * 获取系统产品列表
     * @param request
     * @return
     */
    List<SysProductVO> getList(SysProductQryDTO request);

    /**
     * 系统产品启停用
     * @param sysProductId
     */
    void enableSwitch(SysProductStatusDTO request);
}
