package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.po.category.HomeAutoCategoryAttribute;

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
}
