package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.sys_product.SysCategoryAttribute;
import com.landleaf.homeauto.center.device.model.vo.sys_product.SysCategoryAttributeDTO;
import com.landleaf.homeauto.center.device.model.vo.sys_product.SysCategoryAttributeVO;
import com.landleaf.homeauto.center.device.model.vo.sys_product.SysProductCategoryDTO;
import com.landleaf.homeauto.center.device.model.vo.sys_product.SysProductCategoryVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lokiy
 * @since 2021-05-25
 */
public interface ISysCategoryAttributeService extends IService<SysCategoryAttribute> {

    /**
     * 获取系统品类属性-属性值-VO 页面回显
     * @param sysProductId
     * @return
     */
    List<SysCategoryAttributeVO> getListAttrVOBySysProductId(Long sysProductId);

    /**
     * 获取系统品类属性-属性值 DTO
     * @param sysProductId
     * @return
     */
    List<SysCategoryAttributeDTO> getListAttrDTOBySysProductId(Long sysProductId);


}
