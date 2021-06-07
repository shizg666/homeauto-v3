package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrCategoryBO;
import com.landleaf.homeauto.center.device.model.domain.sysproduct.SysProduct;
import com.landleaf.homeauto.center.device.model.vo.product.ProductInfoSelectVO;
import com.landleaf.homeauto.center.device.model.vo.sys_product.*;
import com.landleaf.homeauto.common.domain.vo.SelectedLongVO;

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
    Long addSysProduct(SysProductDTO requestDTO);

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
     * @param request
     */
    void enableSwitch(SysProductStatusDTO request);

    /**
     * 新增系统设备时获取品类下的产品下拉列表
     * @param categoryCode
     * @return
     */
    List<ProductInfoSelectVO> getListProductSelectByCategoryCode(String categoryCode);

    /**
     * 获取系统产品下拉列表
     * @return
     */
    List<SelectedLongVO> getSelectList();

    /**
     * 根据项目id获取项目绑定的系统信息
     * @param projectId
     * @return
     */
    SysProduct getSysProductByProjectId(Long projectId);

    /**
     * 根据id集合获取系统产品信息
     * @param sysPids
     * @return
     */
    List<SysProduct> getSysProductByPids(List<Long> sysPids);
}
