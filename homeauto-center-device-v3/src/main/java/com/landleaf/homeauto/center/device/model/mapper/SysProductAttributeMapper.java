package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.sys_product.SysProductAttribute;
import com.landleaf.homeauto.center.device.model.vo.sys_product.SysProductAttributeDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lokiy
 * @since 2021-05-24
 */
public interface SysProductAttributeMapper extends BaseMapper<SysProductAttribute> {

    /**
     * 获取系统产品属性 DTO对象
     * @param sysProductId
     * @return
     */
    List<SysProductAttributeDTO> getListAttrDTOBySysProductId(@Param("sysProductId") Long sysProductId);
}
