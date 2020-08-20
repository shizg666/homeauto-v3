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

    List<CategoryAttributeVO> getAttributesByCategoryIds(List<String> categoryIds);

    /**
     * 根据品类主键id查询品类下拉的属性集合
     * @param categoryId
     * @return
     */
    List<SelectedVO> getListAttrbute(String categoryId);

    /**
     * 根据品类主键id查询品类下的属性集合
     * @return
     */
    CategoryAttributeDTO getAttrbuteDetail(CategoryAttrQryDTO request);
}
