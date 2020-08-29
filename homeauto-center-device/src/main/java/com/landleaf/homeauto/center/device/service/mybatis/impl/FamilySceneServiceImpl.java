package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.bo.FamilySceneBO;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilySceneMapper;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 家庭情景表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Service
public class FamilySceneServiceImpl extends ServiceImpl<FamilySceneMapper, FamilySceneDO> implements IFamilySceneService {

    @Autowired
    private FamilySceneMapper familySceneMapper;

    @Override
    public List<FamilySceneBO> getAllSceneList(String familyId) {
        return familySceneMapper.getAllScenesByFamilyId(familyId);
    }

    @Override
    public List<FamilySceneBO> getCommonSceneList(String familyId) {
        return familySceneMapper.getCommonScenesByFamilyId(familyId);
    }

    @Override
    public List<FamilySceneDO> getFamilyScenesBySceneId(String sceneId) {
        FamilySceneDO familySceneDO = getById(sceneId);
        QueryWrapper<FamilySceneDO> familySceneQueryWrapper = new QueryWrapper<>();
        familySceneQueryWrapper.eq("family_id", familySceneDO.getFamilyId());
        return list(familySceneQueryWrapper);
    }

}
