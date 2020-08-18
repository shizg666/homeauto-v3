package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoCategoryAttribute;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.CategoryAttrQryDTO;
import com.landleaf.homeauto.common.domain.vo.category.CategoryAttributeDTO;

import java.util.List;

/**
 * <p>
 * 品类属性信息表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
public interface IHomeAutoCategoryAttributeService extends IService<HomeAutoCategoryAttribute> {

    /**
     * 根据品类id查询属性id
     * @param id
     * @return
     */
    List<String> getIdListByCategoryId(String id);

    /**
     * 根据品类主键id查询品类下的属性集合
     * @return
     */
    CategoryAttributeDTO getAttrbuteDetail(CategoryAttrQryDTO request);

    /**
     * 根据品类主键id查询品类下拉的属性集合
     * @param categoryId
     * @return
     */
    List<SelectedVO> getListAttrbute(String categoryId);
}
