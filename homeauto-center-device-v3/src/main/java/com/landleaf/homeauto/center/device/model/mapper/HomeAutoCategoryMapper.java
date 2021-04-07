package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoCategory;
import com.landleaf.homeauto.common.domain.vo.category.AttributeCascadeVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 品类表 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
public interface HomeAutoCategoryMapper extends BaseMapper<HomeAutoCategory> {

    /**
     * 判断某个类别的设备是否存在
     * @param type
     * @return
     */
    int countDeviceByCategoryType(@Param("type") Integer type);

    @Select("select code from home_auto_category where id = #{id}")
    String getTypeById(@Param("id") Long id);


    @Select("select protocol from home_auto_category where id = #{categoryId}")
    String getProtocolsByid(@Param("categoryId")String categoryId);

    @Select("select c.code from home_auto_category c where c.id = #{categoryId}")
    String getCategoryCodeById(@Param("categoryId") Long categoryId);

    /**
     * 判断品类下是否是产品
     * @param categoryId
     * @return
     */
    @Select("select count(id) from home_auto_product p where p.category_id = #{categoryId} limit 1")
    int exsitCategoryProduct(@Param("categoryId") Long categoryId);
}
