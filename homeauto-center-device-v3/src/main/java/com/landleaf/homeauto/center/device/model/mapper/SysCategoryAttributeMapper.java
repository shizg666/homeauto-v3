package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.sys_product.SysCategoryAttribute;
import com.landleaf.homeauto.center.device.model.vo.sys_product.SysCategoryAttributeDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lokiy
 * @since 2021-05-25
 */
public interface SysCategoryAttributeMapper extends BaseMapper<SysCategoryAttribute> {

    /**
     * 获取系统关联品类的属性和属性值
     * @param sysProductId
     * @return
     */
    List<SysCategoryAttributeDTO> getListAttrDTOBySysProductId(@Param("sysProductId") Long sysProductId);
}
