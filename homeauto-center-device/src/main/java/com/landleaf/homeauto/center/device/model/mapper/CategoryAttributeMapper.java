package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.category.CategoryAttribute;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.CategoryAttrQryDTO;
import com.landleaf.homeauto.common.domain.vo.category.CategoryAttributeDTO;
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

    List<CategoryAttributeVO> getAttributesByCategoryIds(@Param("categoryIds") List<Long> categoryIds);

    List<SelectedVO> getListAttrbute(@Param("categoryId")String categoryId);

    CategoryAttributeDTO getAttrbuteDetail(CategoryAttrQryDTO request);

    /**
     * 获取品类下的属性和属性值信息
     * @param categoryId
     * @return
     */
    List<CategoryAttributeDTO> getListAttrbuteInfo(@Param("categoryId")String categoryId);
}
