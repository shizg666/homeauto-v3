package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceWithPositionBO;
import com.landleaf.homeauto.center.device.model.bo.FamilySceneBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonSceneDO;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneDO;
import com.landleaf.homeauto.center.device.model.dto.FamilySceneCommonDTO;
import com.landleaf.homeauto.center.device.model.mapper.FamilySceneMapper;
import com.landleaf.homeauto.center.device.model.vo.FamilySceneVO;
import com.landleaf.homeauto.center.device.model.vo.SceneDetailVO;
import com.landleaf.homeauto.center.device.model.vo.TimingSceneDetailVO;
import com.landleaf.homeauto.center.device.model.vo.TimingSceneVO;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 家庭情景表 服务实现类
 * </p>
 *
 * <p>
 * 关于获取常用场景和非常用场景为什么要用补集和交集的方式, 这里作出解释.
 * 因为在APP中, 场景排序需要排序值, 而这个序号只在常用场景中存在.
 * 当APP点击添加场景后, 除了要展示常用的场景,还要展示非常用的场景,而非
 * 常用的场景是没有排序值的,如果人为的给它添加序号,当常用场景发生改变时,
 * 非常用场景的排序值和常用场景的排序值就会冲突, 进而导致排序混乱.
 * </p>
 *
 * <p>
 * 所以, 为了避免这个问题, 我选择先查询所有的场景作为全集, 再人为地对场
 * 景进行编号, 作为场景的排序值. 然后查询常用场景, 取全集和常用场景的交
 * 集便是常用场景; 取常用场景在全集中的补集便是非常用场景.
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

    private IFamilyCommonSceneService familyCommonSceneService;

    @Override
    public List<FamilySceneVO> getCommonScenesByFamilyId(String familyId) {
        // 先获取所有场景
        List<FamilySceneBO> allSceneBOList = familySceneMapper.getAllScenesByFamilyId(familyId);
        // 再获取常用场景
        List<FamilySceneBO> commonSceneBOList = familySceneMapper.getCommonScenesByFamilyId(familyId);
        // 所有场景与常用场景的交集就是常用场景
        // 下面的代码应该是这样的:
        //          for (FamilySceneBO familySceneBO : allSceneBOList) {
        //              if (!commonSceneBOList.contains(familySceneBO)){
        //                  allSceneBOList.remove(familySceneBO);
        //              }
        //          }
        allSceneBOList.removeIf(familySceneBO -> !commonSceneBOList.contains(familySceneBO));

        return handleResult(allSceneBOList);
    }

    @Override
    public List<FamilySceneVO> getUncommonScenesByFamilyId(String familyId) {
        // 先获取所有的场景
        List<FamilySceneBO> allSceneBOList = familySceneMapper.getAllScenesByFamilyId(familyId);
        // 再获取常用场景
        List<FamilySceneBO> commonSceneBOList = familySceneMapper.getCommonScenesByFamilyId(familyId);
        // 全部场景中,常用场景的补集就是不常用场景
        allSceneBOList.removeAll(commonSceneBOList);
        return handleResult(allSceneBOList);
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
            sceneDetailVO.setDeviceAttrs(familySceneActionService.getDeviceActionAttributionOnMapByDeviceSn(deviceWithPositionBO.getDeviceSn()));
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertFamilyCommonScene(FamilySceneCommonDTO familySceneCommonDTO) {
        // 先删除原来的常用场景
        QueryWrapper<FamilyCommonSceneDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("family_id", familySceneCommonDTO.getFamilyId());
        familyCommonSceneService.remove(queryWrapper);

        // 再把新的常用场景添加进去
        List<FamilyCommonSceneDO> familyCommonSceneDOList = new LinkedList<>();
        for (String sceneId : familySceneCommonDTO.getScenes()) {
            FamilyCommonSceneDO familyCommonSceneDO = new FamilyCommonSceneDO();
            familyCommonSceneDO.setFamilyId(familySceneCommonDTO.getFamilyId());
            familyCommonSceneDO.setSceneId(sceneId);
            familyCommonSceneDO.setSortNo(0);
            familyCommonSceneDOList.add(familyCommonSceneDO);
        }
        familyCommonSceneService.saveBatch(familyCommonSceneDOList);
    }

    @Override
    public boolean isSceneExists(String sceneId) {
        return !Objects.isNull(getById(sceneId));
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

    @Autowired
    public void setFamilyCommonSceneService(IFamilyCommonSceneService familyCommonSceneService) {
        this.familyCommonSceneService = familyCommonSceneService;
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
