package com.landleaf.homeauto.center.device.service.mybatis.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonSceneDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilyCommonSceneMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyCommonSceneService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 家庭常用场景表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Service
public class FamilyCommonSceneServiceImpl extends ServiceImpl<FamilyCommonSceneMapper, FamilyCommonSceneDO> implements IFamilyCommonSceneService {

    @Override
    public List<String> getCommonSceneIdListByFamilyId(String familyId) {
        QueryWrapper<FamilyCommonSceneDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("scene_id");
        queryWrapper.eq("family_id", familyId);
        List<FamilyCommonSceneDO> familyCommonSceneDOList = list(queryWrapper);
        List<String> sceneIdList = CollectionUtil.list(true);
        for (FamilyCommonSceneDO familyCommonSceneDO : familyCommonSceneDOList) {
            sceneIdList.add(familyCommonSceneDO.getSceneId());
        }
        return sceneIdList;
    }
}
