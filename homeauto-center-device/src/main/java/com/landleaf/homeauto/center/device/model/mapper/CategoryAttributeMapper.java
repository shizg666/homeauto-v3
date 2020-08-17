package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.common.domain.po.category.CategoryAttribute;
import com.landleaf.homeauto.common.domain.vo.category.CategoryAttributeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 品类属性信息表 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
public interface CategoryAttributeMapper extends BaseMapper<CategoryAttribute> {

    List<CategoryAttributeVO> getAttributesByCategoryIds(@Param("categoryIds") List<String> categoryIds);
}
