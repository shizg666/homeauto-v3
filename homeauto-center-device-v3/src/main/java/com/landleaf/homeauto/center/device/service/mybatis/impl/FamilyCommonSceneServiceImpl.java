package com.landleaf.homeauto.center.device.service.mybatis.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonSceneDO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.HouseTemplateScene;
import com.landleaf.homeauto.center.device.model.mapper.FamilyCommonSceneMapper;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilySceneBO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyCommonSceneService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateSceneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private IHomeAutoFamilyService familyService;

    @Autowired
    private IHouseTemplateSceneService houseTemplateSceneService;

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

    @Override
    public void deleteFamilySceneCommonScene(String familyId, String sceneId) {
        QueryWrapper<FamilyCommonSceneDO> sceneQueryWrapper = new QueryWrapper<>();
        sceneQueryWrapper.eq("scene_id", sceneId);
        sceneQueryWrapper.eq("family_id", familyId);
        remove(sceneQueryWrapper);
    }

    @Override
    public boolean isExist(String familyId, String sceneId) {
        QueryWrapper<FamilyCommonSceneDO> sceneQueryWrapper = new QueryWrapper<>();
        sceneQueryWrapper.eq("scene_id", sceneId);
        sceneQueryWrapper.eq("family_id", familyId);
        return count(sceneQueryWrapper) > 0;
    }

    @Override
    public List<FamilyCommonSceneDO> listCommonScenesByFamilyId(String familyId) {
        QueryWrapper<FamilyCommonSceneDO> familyCommonSceneEntityQueryWrapper = new QueryWrapper<>();
        familyCommonSceneEntityQueryWrapper.eq("family_id", familyId);
        return list(familyCommonSceneEntityQueryWrapper);
    }

    @Override
    public void deleteByFamilyId(String familyId) {
        QueryWrapper<FamilyCommonSceneDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("family_id", familyId);
        remove(queryWrapper);
    }
    /**
     *  APP保存常用场景
     * @param familyId  家庭ID
     * @param sceneIds 场景Ids
     * @return void
     * @author wenyilu
     * @date 2021/1/12 13:30
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCommonSceneList(String familyId, List<String> sceneIds) {
        HomeAutoFamilyDO familyDO = familyService.getById(familyId);
        // 1. 删除家庭常用场景
        deleteByFamilyId(familyId);

        // 2. 再把新的常用场景添加进去
        List<FamilyCommonSceneDO> familyCommonSceneList = new LinkedList<>();
        for (int i = 0; i < sceneIds.size(); i++) {
            String sceneId = sceneIds.get(i);
            FamilyCommonSceneDO familyCommonSceneDO = new FamilyCommonSceneDO();
            familyCommonSceneDO.setFamilyId(familyId);
            familyCommonSceneDO.setSceneId(sceneId);
            familyCommonSceneDO.setSortNo(i);
            familyCommonSceneDO.setTemplateId(familyDO.getTemplateId());
            familyCommonSceneList.add(familyCommonSceneDO);
        }
        saveBatch(familyCommonSceneList);
    }

    @Override
    public List<FamilySceneVO> getCommonScenesByFamilyId4VO(String familyId, String templateId) {
        // 获取户型下场景列表
        List<HouseTemplateScene> scenes = houseTemplateSceneService.getScenesByTemplate(Long.parseLong(templateId));
        // 获取家庭下常用场景列表
        List<FamilyCommonSceneDO> familyCommonSceneDOS = listCommonScenesByFamilyId(familyId);
        List<FamilySceneVO> result = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(familyCommonSceneDOS) && !CollectionUtils.isEmpty(scenes)) {
            List<FamilySceneBO> familySceneBOList = houseTemplateSceneService.getFamilySceneWithIndex(familyId,
                    templateId,scenes, familyCommonSceneDOS, true);
            if (!CollectionUtils.isEmpty(familySceneBOList)) {
                result.addAll(familySceneBOList.stream().map(familySceneBO -> {
                    FamilySceneVO familySceneVO = new FamilySceneVO();
                    familySceneVO.setSceneId(familySceneBO.getSceneId());
                    familySceneVO.setSceneName(familySceneBO.getSceneName());
                    familySceneVO.setSceneIcon(familySceneBO.getSceneIcon());
                    familySceneVO.setSceneIndex(familySceneBO.getSceneIndex());
                    return familySceneVO;
                }).collect(Collectors.toList()));
            }
        }
        return result;
    }

    @Override
    public List<FamilySceneVO> getFamilyUncommonScenes4VOByFamilyId(String familyId) {
        HomeAutoFamilyDO familyDO = familyService.getById(familyId);
        List<HouseTemplateScene> scenes = houseTemplateSceneService.getScenesByTemplate(Long.parseLong(familyDO.getTemplateId()));

        List<FamilyCommonSceneDO> familyCommonSceneDOList = listCommonScenesByFamilyId(familyId);
        List<FamilySceneBO> familySceneBOList = houseTemplateSceneService.getFamilySceneWithIndex(familyId,familyDO.getTemplateId(),scenes, familyCommonSceneDOList, false);
        List<FamilySceneVO> familySceneVOList = new LinkedList<>();
        for (FamilySceneBO familySceneBO : familySceneBOList) {
            FamilySceneVO familySceneVO = new FamilySceneVO();
            familySceneVO.setSceneId(familySceneBO.getSceneId());
            familySceneVO.setSceneName(familySceneBO.getSceneName());
            familySceneVO.setSceneIcon(familySceneBO.getSceneIcon());
            familySceneVO.setSceneIndex(familySceneBO.getSceneIndex());
            familySceneVOList.add(familySceneVO);
        }
        return familySceneVOList;
    }
}
