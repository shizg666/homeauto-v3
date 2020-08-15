package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.po.category.CategoryAttribute;
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
}
