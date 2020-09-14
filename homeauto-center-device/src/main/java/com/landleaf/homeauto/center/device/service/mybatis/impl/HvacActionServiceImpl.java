package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.HvacAction;
import com.landleaf.homeauto.center.device.model.mapper.HvacActionMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IHvacActionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 场景暖通动作配置 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-28
 */
@Service
public class HvacActionServiceImpl extends ServiceImpl<HvacActionMapper, HvacAction> implements IHvacActionService {

    @Override
    public List<String> getListIds(List<String> hvacConfigIds) {
        if (CollectionUtils.isEmpty(hvacConfigIds)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        return this.baseMapper.getListIds(hvacConfigIds);
    }
}
