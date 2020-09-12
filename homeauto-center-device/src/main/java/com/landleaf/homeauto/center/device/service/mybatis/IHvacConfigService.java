package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.HvacConfig;

import java.util.List;

/**
 * <p>
 * 场景暖通配置 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-28
 */
public interface IHvacConfigService extends IService<HvacConfig> {

    /**
     * 根据设备号和户型id获取该设备关联的场景暖通配置主键集合
     * @param deviceSn
     * @param houseTemplateId
     * @return
     */
    List<String> getListIds(String deviceSn, String houseTemplateId);
}
