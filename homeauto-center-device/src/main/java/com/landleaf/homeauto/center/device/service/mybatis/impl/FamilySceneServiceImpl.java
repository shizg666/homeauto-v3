package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceWithPositionBO;
import com.landleaf.homeauto.center.device.model.bo.FamilySceneBO;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilySceneMapper;
import com.landleaf.homeauto.center.device.model.vo.FamilySceneVO;
import com.landleaf.homeauto.center.device.model.vo.SceneDetailVO;
import com.landleaf.homeauto.center.device.model.vo.TimingSceneDetailVO;
import com.landleaf.homeauto.center.device.model.vo.TimingSceneVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneActionService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneTimingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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

    private FamilySceneMapper familySceneMapper;

    private IFamilyDeviceService familyDeviceService;

    private IFamilySceneActionService familySceneActionService;

    private IFamilySceneTimingService familySceneTimingService;

    @Override
    public List<FamilySceneVO> getCommonScenesByFamilyId(String familyId) {
        List<FamilySceneBO> commonSceneBOList = familySceneMapper.getCommonScenesByFamilyId(familyId);
        return handleResult(commonSceneBOList);
    }

    @Override
    public List<FamilySceneVO> getUncommonScenesByFamilyId(String familyId) {
        // 先获取所有的场景
        List<FamilySceneBO> allScenesBOList = familySceneMapper.getAllScenesByFamilyId(familyId);
        List<FamilySceneBO> commonSceneBOList = familySceneMapper.getCommonScenesByFamilyId(familyId);
        for (FamilySceneBO commonScene : commonSceneBOList) {
            allScenesBOList.removeIf(familySceneBO -> Objects.equals(familySceneBO.getSceneId(), commonScene.getSceneId()));
        }
        return handleResult(allScenesBOList);
    }

    @Override
    public List<FamilySceneVO> getWholeHouseScenesByFamilyId(String familyId) {
        QueryWrapper<FamilySceneDO> familySceneQueryWrapper = new QueryWrapper<>();
        familySceneQueryWrapper.select("id", "name", "icon");
        familySceneQueryWrapper.eq("type", 1);
        familySceneQueryWrapper.eq("family_id", familyId);
        List<FamilySceneDO> familyScenePoList = list(familySceneQueryWrapper);
        List<FamilySceneVO> familySceneVOList = new LinkedList<>();
        for (FamilySceneDO familyScenePo : familyScenePoList) {
            FamilySceneVO familySceneVO = new FamilySceneVO();
            familySceneVO.setSceneId(familyScenePo.getId());
            familySceneVO.setSceneName(familyScenePo.getName());
            familySceneVO.setSceneIcon(familyScenePo.getIcon());
            familySceneVOList.add(familySceneVO);
        }
        return familySceneVOList;
    }

    @Override
    public List<SceneDetailVO> getSceneDetailBySceneId(String sceneId) {
        List<FamilyDeviceWithPositionBO> familyDeviceWithPositionBOList = familyDeviceService.getDeviceInfoBySceneId(sceneId);
        List<SceneDetailVO> sceneDetailVOList = new LinkedList<>();
        for (FamilyDeviceWithPositionBO deviceWithPositionBO : familyDeviceWithPositionBOList) {
            SceneDetailVO sceneDetailVO = new SceneDetailVO();
            sceneDetailVO.setDeviceId(deviceWithPositionBO.getDeviceId());
            sceneDetailVO.setDeviceName(deviceWithPositionBO.getDeviceName());
            sceneDetailVO.setDeviceIcon(deviceWithPositionBO.getDeviceIcon());
            sceneDetailVO.setDevicePosition(String.format("%s-%s", deviceWithPositionBO.getFloorName(), deviceWithPositionBO.getRoomName()));
            sceneDetailVO.setDeviceAttrs(familySceneActionService.getDeviceActionAttributionByDeviceSn(deviceWithPositionBO.getDeviceSn()));
            sceneDetailVOList.add(sceneDetailVO);
        }
        return sceneDetailVOList;
    }

    @Override
    public List<TimingSceneVO> getTimingScenesByFamilyId(String familyId) {
        return familySceneTimingService.getTimingScenesByFamilyId(familyId);
    }

    @Override
    public TimingSceneDetailVO getTimingSceneDetailByTimingId(String timingId) {
        return familySceneTimingService.getTimingSceneDetailByTimingId(timingId);
    }

    @Override
    public List<FamilySceneDO> getFamilyScenesBySceneId(String sceneId) {
        FamilySceneDO familySceneDO = baseMapper.selectById(sceneId);
        QueryWrapper<FamilySceneDO> familySceneQueryWrapper = new QueryWrapper<>();
        familySceneQueryWrapper.eq("family_id", familySceneDO.getFamilyId());
        return list(familySceneQueryWrapper);
    }

    @Autowired
    public void setFamilySceneMapper(FamilySceneMapper familySceneMapper) {
        this.familySceneMapper = familySceneMapper;
    }

    @Autowired
    public void setFamilyDeviceService(IFamilyDeviceService familyDeviceService) {
        this.familyDeviceService = familyDeviceService;
    }

    @Autowired
    public void setFamilySceneActionService(IFamilySceneActionService familySceneActionService) {
        this.familySceneActionService = familySceneActionService;
    }

    @Autowired
    public void setFamilySceneTimingService(IFamilySceneTimingService familySceneTimingService) {
        this.familySceneTimingService = familySceneTimingService;
    }

    /**
     * 将BO对象转换为VO对象
     *
     * @param sceneBOList 场景业务对象集合
     * @return 场景视图对象集合
     */
    private List<FamilySceneVO> handleResult(List<FamilySceneBO> sceneBOList) {
        List<FamilySceneVO> sceneVOList = new LinkedList<>();
        for (FamilySceneBO commonSceneBO : sceneBOList) {
            FamilySceneVO commonSceneVO = new FamilySceneVO();
            commonSceneVO.setSceneId(commonSceneBO.getSceneId());
            commonSceneVO.setSceneName(commonSceneBO.getSceneName());
            commonSceneVO.setSceneIcon(commonSceneBO.getSceneIcon());
            commonSceneVO.setIndex(commonSceneBO.getIndex());
            sceneVOList.add(commonSceneVO);
        }
        return sceneVOList;
    }
}
