package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.common.domain.po.category.HomeAutoCategory;
import org.apache.ibatis.annotations.Param;

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
}
