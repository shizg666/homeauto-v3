package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.sys_product.SysProduct;
import com.landleaf.homeauto.center.device.model.domain.sys_product.SysProductCategory;
import com.landleaf.homeauto.center.device.model.vo.sys_product.SysProductAttributeDTO;
import com.landleaf.homeauto.center.device.model.vo.sys_product.SysProductAttributeVO;
import com.landleaf.homeauto.center.device.model.vo.sys_product.SysProductCategoryDTO;

import java.util.List;

/**
 * <p>
 * 系统产品关联品类表 服务类
 * </p>
 *
 * @author lokiy
 * @since 2021-05-24
 */
public interface ISysProductCategoryService extends IService<SysProductCategory> {

    /**
     * 批量新增系统类别属性
     * @param sysProductId 系统产品id
     * @param sysProductCode 系统产品code
     * @param categorys
     */
    void saveBathCategorAttribute(Long sysProductId, String sysProductCode, List<SysProductCategoryDTO> categorys);


    /**
     *批量新增系统绑定的产品
     * @param sysProductId 系统产品id
     * @param sysProductCode 系统产品code
     * @param categorys
     */
    void saveBathProductCategory(Long sysProductId, String sysProductCode, List<SysProductCategoryDTO> categorys);

    /**
     * 根据系统产品id删除关联的品类信息
     * @param sysProductId
     */
    void deleteBySysProductId(Long sysProductId);

    /**
     * 获取系统产品属性 VO对象
     * @param sysProductId
     * @return
     */
    List<SysProductAttributeDTO> getListAttrDTOBySysProductId(Long sysProductId);

    /**
     * 获取系统产品属性 VO对象
     * @param sysProductId
     * @return
     */
    List<SysProductAttributeVO> getListAttrVOBySysProductId(Long sysProductId);
}
