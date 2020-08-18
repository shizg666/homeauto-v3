package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoCategoryAttribute;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 品类属性信息表 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
public interface HomeAutoCategoryAttributeMapper extends BaseMapper<HomeAutoCategoryAttribute> {

    List<String> getIdListByCategoryId(@Param("id") String id);
}
