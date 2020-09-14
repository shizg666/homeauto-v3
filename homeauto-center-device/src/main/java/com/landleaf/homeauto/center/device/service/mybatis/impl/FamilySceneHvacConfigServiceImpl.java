package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneHvacConfig;
import com.landleaf.homeauto.center.device.model.mapper.FamilySceneHvacConfigMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneHvacConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/9/2
 */
@Service
public class FamilySceneHvacConfigServiceImpl extends ServiceImpl<FamilySceneHvacConfigMapper, FamilySceneHvacConfig> implements IFamilySceneHvacConfigService {

    @Override
    public List<String> getListIds(String deviceSn, String familyId) {
        return this.baseMapper.getListIds(deviceSn,familyId);
    }
}
