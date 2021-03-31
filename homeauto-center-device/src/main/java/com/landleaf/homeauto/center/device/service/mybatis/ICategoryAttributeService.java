package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.category.CategoryAttribute;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.CategoryAttrQryDTO;
import com.landleaf.homeauto.common.domain.vo.category.CategoryAttributeDTO;
import com.landleaf.homeauto.common.domain.vo.category.CategoryAttributeVO;

import java.util.List;

/**
 * <p>
 * 品类属性信息表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
public interface ICategoryAttributeService extends IService<CategoryAttribute> {

    /**
     * 根据品类集合获取品类包含的功能属性信息
     * @param categoryIds
     * @return
     */
    List<CategoryAttributeVO> getAttributesByCategoryIds(List<Long> categoryIds);

    /**
     * 根据品类主键id查询品类下拉的属性集合
     * @param categoryId
     * @return
     */
    List<SelectedVO> getListAttrbute(String categoryId);

    /**
     * 根据品类主键id和属性code获取属性的详细信息
     * @return
     */
    CategoryAttributeDTO getAttrbuteDetail(CategoryAttrQryDTO request);

    /**
     * 获取品类下的属性和属性值信息
     * @param categoryId
     * @return
     */
    List<CategoryAttributeDTO> getListAttrbuteInfo(String categoryId);
}
