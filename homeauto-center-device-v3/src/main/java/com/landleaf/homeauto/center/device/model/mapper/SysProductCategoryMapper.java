package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.sysproduct.SysProductCategory;
import com.landleaf.homeauto.center.device.model.vo.project.CountLongBO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    /**
     * 获取系统产品的关联的品类code列表
     * @param sysPid
     * @return
     */
    @Select("SELECT sc.category_code from sys_product_category sc where sc.sys_product_id = #{sysPid}")
    List<String> getListCategoryBySysPid(@Param("sysPid") Long sysPid);

    /**
     * 获取系统产品所绑定的某一品类可配置的数量
     * @param sysPid
     * @param categoryCode
     * @return
     */
    @Select("select pc.category_num from sys_product_category as pc where pc.sys_product_id =#{sysPid} and pc.category_code = #{categoryCode}'")
    int getCategoryNumBySysPid(@Param("sysPid") Long sysPid, @Param("categoryCode")String categoryCode);
}
