package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.HvacAction;

import java.util.List;

/**
 * <p>
 * 场景暖通动作配置 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-28
 */
public interface IHvacActionService extends IService<HvacAction> {

    /**
     * 根据暖通配置id集合获取暖通动作id集合
     * @param hvacConfigIds
     * @return
     */
    List<String> getListIds(List<String> hvacConfigIds);
}
