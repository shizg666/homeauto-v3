package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.FamilyScene;
import com.landleaf.homeauto.center.device.model.mapper.FamilySceneMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneService;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateSceneService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 家庭情景表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-28
 */
@Service
public class FamilySceneServiceImpl extends ServiceImpl<FamilySceneMapper, FamilyScene> implements IFamilySceneService {


    @Override
    public List<FamilyScene> getListSceneByfId(Long familyId) {
        List<FamilyScene> data = list(new LambdaQueryWrapper<FamilyScene>().eq(FamilyScene::getFamilyId,familyId).select(FamilyScene::getName,FamilyScene::getIcon,FamilyScene::getId));
        return data;
    }
}
