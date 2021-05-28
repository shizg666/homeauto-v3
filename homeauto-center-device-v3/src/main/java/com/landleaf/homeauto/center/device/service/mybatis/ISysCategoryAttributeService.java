package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.sys_product.SysCategoryAttribute;
import com.landleaf.homeauto.center.device.model.vo.product.ProductAttrInfoBO;
import com.landleaf.homeauto.center.device.model.vo.sys_product.SysCategoryAttributeDTO;
import com.landleaf.homeauto.center.device.model.vo.sys_product.SysCategoryAttributeVO;

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

    /**
     * 获取系统某一品类下的属性和属性值信息
     * @param categoryCode
     * @return
     */
    List<ProductAttrInfoBO> getAttributeAndValByCategoryCode(String categoryCode);
}
