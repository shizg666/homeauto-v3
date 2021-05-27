package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.sys_product.SysProductCategory;
import com.landleaf.homeauto.center.device.model.vo.project.CountLongBO;
import com.landleaf.homeauto.center.device.model.vo.sys_product.SysProductAttributeDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统产品关联品类表 Mapper 接口
 * </p>
 *
 * @author lokiy
 * @since 2021-05-24
 */
public interface SysProductCategoryMapper extends BaseMapper<SysProductCategory> {


    /**
     * 获取系统产品下品类数量
     * @param sysPids
     * @return
     */
    List<CountLongBO> getCountBySysPids(@Param("sysPids") List<Long> sysPids);

    /**
     * 获取系统产品关联的品类 在项目中是否配置了设备， 配置了的话 是不可以修改的
     * @param sysPid
     * @return
     */
    List<String> getCategoryUpdateFalg(@Param("sysPid") Long sysPid);
}
