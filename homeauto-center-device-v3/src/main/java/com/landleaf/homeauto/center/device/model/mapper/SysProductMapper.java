package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.sysproduct.SysProduct;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 系统产品表 Mapper 接口
 * </p>
 *
 * @author lokiy
 * @since 2021-05-24
 */
public interface SysProductMapper extends BaseMapper<SysProduct> {

    /**
     * 判断系统是否被项目绑定
     * @param sysPid
     */
    int isUserdProject(@Param("sysPid") Long sysPid);

    SysProduct getSysProductByProjectId(@Param("projectId") Long projectId);
}
