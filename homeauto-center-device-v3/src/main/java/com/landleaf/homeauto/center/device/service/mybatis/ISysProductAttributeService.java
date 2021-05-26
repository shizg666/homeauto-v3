package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.sys_product.SysProductAttribute;
import com.landleaf.homeauto.center.device.model.vo.sys_product.SysProductAttributeDTO;
import com.landleaf.homeauto.center.device.model.vo.sys_product.SysProductAttributeVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lokiy
 * @since 2021-05-24
 */
public interface ISysProductAttributeService extends IService<SysProductAttribute> {

    /**
     * a删除系统产品属性
     * @param sysProductId
     */
    void deleteProductAttribures(Long sysProductId);

    /**
     * 获取系统产品属性 DTO对象
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
