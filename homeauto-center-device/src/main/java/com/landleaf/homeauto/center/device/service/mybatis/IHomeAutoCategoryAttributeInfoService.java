package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.po.category.HomeAutoCategoryAttributeInfo;
import com.landleaf.homeauto.common.domain.vo.category.AttributeInfoDicDTO;

import java.util.List;

/**
 * <p>
 * 品类属性值表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
public interface IHomeAutoCategoryAttributeInfoService extends IService<HomeAutoCategoryAttributeInfo> {

    /**
     * 根据品类id和属性code获取属性值列表
     * @param code
     * @return
     */
    List<AttributeInfoDicDTO> getListByAttributeCode(String categoryId,String code);
}
