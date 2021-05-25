package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.sys_product.SysProductAttribute;

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
}
