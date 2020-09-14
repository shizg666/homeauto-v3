package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.HvacConfig;
import com.landleaf.homeauto.center.device.model.mapper.HvacConfigMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IHvacConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 场景暖通配置 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-28
 */
@Service
public class HvacConfigServiceImpl extends ServiceImpl<HvacConfigMapper, HvacConfig> implements IHvacConfigService {


    @Override
    public List<String> getListIds(String deviceSn, String houseTemplateId) {
        return this.baseMapper.getListIds(deviceSn,houseTemplateId);
    }
}
