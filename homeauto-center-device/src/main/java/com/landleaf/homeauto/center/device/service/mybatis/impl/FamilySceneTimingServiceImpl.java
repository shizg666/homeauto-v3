package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.bo.FamilySceneTimingBO;
import com.landleaf.homeauto.center.device.model.constant.FamilySceneTimingRepeatTypeEnum;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneTimingDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilySceneTimingMapper;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneTimingVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneTimingService;
import com.landleaf.homeauto.center.device.util.DateUtils;
import com.landleaf.homeauto.common.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public List<FamilySceneTimingBO> getTimingScenesByFamilyId(String familyId) {
        return familySceneTimingMapper.getSceneTimingByFamilyId(familyId);
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
    public void updateEnabled(String sceneTimingId, boolean enabled) {
        UpdateWrapper<FamilySceneTimingDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("enable_flag", enabled ? 1 : 0);
        updateWrapper.eq("id", sceneTimingId);
        update(updateWrapper);
    }

}
