package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.HvacAction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 场景暖通动作配置 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-28
 */
public interface HvacActionMapper extends BaseMapper<HvacAction> {

    /**
     *根据暖通配置id集合获取暖通动作id集合
     * @param hvacConfigIds
     * @return
     */
    List<String> getListIds(@Param("hvacConfigIds") List<String> hvacConfigIds);
}
