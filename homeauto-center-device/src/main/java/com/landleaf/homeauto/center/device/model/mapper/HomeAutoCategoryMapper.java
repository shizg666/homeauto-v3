package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.common.domain.po.category.HomeAutoCategory;
import com.landleaf.homeauto.common.domain.vo.category.CategoryAttributeBO;
import com.landleaf.homeauto.common.util.StringUtil;
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

    @Select("select type from home_auto_category where id = #{id}")
    Integer getTypeById(@Param("id") String id);

    /**
     * 根据品类id获取品类属性信息
     * @param id
     * @return
     */
    List<CategoryAttributeBO> getAttributeInfosById(@Param("id") String id);
}
