package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneDO;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneTimingDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilySceneTimingMapper;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilySceneTimingBO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneTimingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 场景定时配置表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Service
public class FamilySceneTimingServiceImpl extends ServiceImpl<FamilySceneTimingMapper, FamilySceneTimingDO> implements IFamilySceneTimingService {

    @Autowired
    private FamilySceneTimingMapper familySceneTimingMapper;

    @Autowired
    private IFamilySceneService familySceneService;

    @Override
    public List<com.landleaf.homeauto.center.device.model.bo.FamilySceneTimingBO> getTimingScenesByFamilyId(String familyId) {
        return familySceneTimingMapper.getSceneTimingByFamilyId(familyId);
    }

    @Override
    public List<FamilySceneTimingBO> listFamilySceneTiming(String familyId) {
        QueryWrapper<FamilySceneTimingDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("family_id", familyId);
        List<FamilySceneTimingDO> familySceneTimingDOList = list(queryWrapper);
        List<FamilySceneTimingBO> familySceneTimingBOList = new LinkedList<>();
        for (FamilySceneTimingDO familySceneTimingDO : familySceneTimingDOList) {
            String sceneId = familySceneTimingDO.getSceneId();
            FamilySceneDO familySceneDO = familySceneService.getById(sceneId);

            FamilySceneTimingBO familySceneTimingBO = new FamilySceneTimingBO();
            familySceneTimingBO.setTimingSceneId(familySceneTimingDO.getId());
            familySceneTimingBO.setExecuteSceneId(familySceneTimingDO.getSceneId());
            familySceneTimingBO.setExecuteSceneName(familySceneDO.getName());
            familySceneTimingBO.setExecuteTime(familySceneTimingDO.getExecuteTime());
            familySceneTimingBO.setEnabled(familySceneTimingDO.getEnableFlag());
            familySceneTimingBO.setSkipHoliday(familySceneTimingDO.getHolidaySkipFlag());
            familySceneTimingBO.setRepeatType(familySceneTimingDO.getType());
            familySceneTimingBO.setWeekday(familySceneTimingDO.getWeekday());
            familySceneTimingBO.setStartDate(familySceneTimingDO.getStartDate());
            familySceneTimingBO.setEndDate(familySceneTimingDO.getEndDate());

            familySceneTimingBOList.add(familySceneTimingBO);
        }
        return familySceneTimingBOList;
    }

    @Override
    public void deleteTimingScene(List<String> timingIds, String familyId) {

        if (CollectionUtils.isEmpty(timingIds) || StringUtils.isEmpty(familyId)) {
            return;
        }
        QueryWrapper<FamilySceneTimingDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", timingIds);
        queryWrapper.eq("family_id", familyId);
        remove(queryWrapper);
    }

    @Override
    public void updateEnabled(String sceneTimingId, Integer enabled) {
        UpdateWrapper<FamilySceneTimingDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("enable_flag", enabled);
        updateWrapper.eq("id", sceneTimingId);
        update(updateWrapper);
    }

}
