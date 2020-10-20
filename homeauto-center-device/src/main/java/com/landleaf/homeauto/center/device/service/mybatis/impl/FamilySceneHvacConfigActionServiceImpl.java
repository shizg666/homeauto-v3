package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneHvacConfigAction;
import com.landleaf.homeauto.center.device.model.mapper.FamilySceneHvacConfigActionMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneHvacConfigActionService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneHvacConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/9/2
 */
@Service
public class FamilySceneHvacConfigActionServiceImpl extends ServiceImpl<FamilySceneHvacConfigActionMapper, FamilySceneHvacConfigAction> implements IFamilySceneHvacConfigActionService {

    @Autowired
    private IFamilySceneHvacConfigService familySceneHvacConfigService;

    @Override
    public List<String> getListIds(List<String> hvacConfigIds) {
        if (CollectionUtils.isEmpty(hvacConfigIds)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        return this.baseMapper.getListIds(hvacConfigIds);
    }

}
