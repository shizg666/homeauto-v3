package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.bo.FamilySceneTimingBO;
import com.landleaf.homeauto.center.device.model.bo.SceneSimpleBO;
import com.landleaf.homeauto.center.device.model.constant.FamilySceneTimingRepeatTypeEnum;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneDO;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneTimingDO;
import com.landleaf.homeauto.center.device.model.dto.TimingSceneDTO;
import com.landleaf.homeauto.center.device.model.mapper.FamilySceneTimingMapper;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneTimingDetailVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneTimingVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneTimingService;
import com.landleaf.homeauto.center.device.util.DateUtils;
import com.landleaf.homeauto.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private FamilySceneTimingMapper familySceneTimingMapper;

    private IFamilySceneService familySceneService;

    @Override
    public List<SceneTimingVO> getTimingScenesByFamilyId(String familyId) {
        List<FamilySceneTimingBO> familySceneTimingDOList = familySceneTimingMapper.getSceneTimingByFamilyId(familyId);
        List<SceneTimingVO> sceneTimingVOList = new LinkedList<>();
        for (FamilySceneTimingBO familySceneTimingBO : familySceneTimingDOList) {
            SceneTimingVO sceneTimingVO = new SceneTimingVO();
            sceneTimingVO.setTimingId(familySceneTimingBO.getTimingId());
            sceneTimingVO.setSceneName(familySceneTimingBO.getSceneName());
            sceneTimingVO.setExecuteTime(DateUtils.toTimeString(familySceneTimingBO.getExecuteTime(), "HH:mm"));
            sceneTimingVO.setEnabled(familySceneTimingBO.getEnabled());

            // 处理重复类型显示
            FamilySceneTimingRepeatTypeEnum sceneTimingRepeatTypeEnum = FamilySceneTimingRepeatTypeEnum.getByType(familySceneTimingBO.getType());
            if (Objects.equals(sceneTimingRepeatTypeEnum, FamilySceneTimingRepeatTypeEnum.NONE)) {
                sceneTimingVO.setWorkday(sceneTimingRepeatTypeEnum.handleWorkDay(null));
            } else if (Objects.equals(sceneTimingRepeatTypeEnum, FamilySceneTimingRepeatTypeEnum.WEEK)) {
                String workDay = sceneTimingRepeatTypeEnum.handleWorkDay(familySceneTimingBO.getWeekday());
                if (Objects.equals(familySceneTimingBO.getSkipHoliday(), 1)) {
                    workDay += "，跳过法定节假日";
                }
                sceneTimingVO.setWorkday(workDay);
            } else {
                String startDateString = DateUtils.toTimeString(familySceneTimingBO.getStartDate(), "yyyy.MM.dd");
                String endDateString = DateUtils.toTimeString(familySceneTimingBO.getEndDate(), "yyyy.MM.dd");
                String timeString = startDateString + "," + endDateString;
                sceneTimingVO.setWorkday(sceneTimingRepeatTypeEnum.handleWorkDay(timeString));
            }
            sceneTimingVOList.add(sceneTimingVO);
        }
        return sceneTimingVOList;
    }

    @Override
    public SceneTimingDetailVO getTimingSceneDetailByTimingId(String timingId) {
        FamilySceneTimingDO familySceneTimingDO = baseMapper.selectById(timingId);
        SceneTimingDetailVO timingSceneDetailVO = new SceneTimingDetailVO();
        timingSceneDetailVO.setTimingId(familySceneTimingDO.getId());
        timingSceneDetailVO.setExecuteTime(DateUtils.toTimeString(familySceneTimingDO.getExecuteTime(), "HH:mm"));
        timingSceneDetailVO.setRepeatType(familySceneTimingDO.getType());
        timingSceneDetailVO.setSkipHoliday(familySceneTimingDO.getHolidaySkipFlag());

        // 重复设置
        if (Objects.equals(FamilySceneTimingRepeatTypeEnum.getByType(familySceneTimingDO.getType()), FamilySceneTimingRepeatTypeEnum.WEEK)) {
            String weekdayInChinese = FamilySceneTimingRepeatTypeEnum.WEEK.replaceWeek(familySceneTimingDO.getWeekday().split(","));
            timingSceneDetailVO.setRepeatValue(String.join(" ", weekdayInChinese.split(",")));
        } else if (Objects.equals(FamilySceneTimingRepeatTypeEnum.getByType(familySceneTimingDO.getType()), FamilySceneTimingRepeatTypeEnum.CALENDAR)) {
            String startDateString = DateUtils.toTimeString(familySceneTimingDO.getStartDate(), "yyyy.MM.dd");
            String endDateString = DateUtils.toTimeString(familySceneTimingDO.getEndDate(), "yyyy.MM.dd");
            timingSceneDetailVO.setRepeatValue(startDateString + "-" + endDateString);
        }

        // 场景设置
        List<FamilySceneDO> familySceneDOList = familySceneService.getFamilyScenesBySceneId(familySceneTimingDO.getSceneId());
        List<SceneSimpleBO> timingSceneVOList = new LinkedList<>();
        for (FamilySceneDO familySceneDO : familySceneDOList) {
            SceneSimpleBO simpleBO = new SceneSimpleBO();
            simpleBO.setSceneId(familySceneDO.getId());
            simpleBO.setSceneName(familySceneDO.getName());
            simpleBO.setSceneIcon(familySceneDO.getIcon());
            simpleBO.setChecked(Objects.equals(familySceneDO.getId(), familySceneTimingDO.getSceneId()) ? 1 : 0);
            timingSceneVOList.add(simpleBO);
        }
        timingSceneDetailVO.setScenes(timingSceneVOList);
        return timingSceneDetailVO;
    }

    @Override
    public String insertOrUpdateFamilySceneTiming(TimingSceneDTO timingSceneDTO) {
        FamilySceneTimingDO familySceneTimingDO = new FamilySceneTimingDO();
        familySceneTimingDO.setId(timingSceneDTO.getTimingId());
        familySceneTimingDO.setSceneId(timingSceneDTO.getSceneId());
        familySceneTimingDO.setExecuteTime(DateUtils.parseLocalTime(timingSceneDTO.getExecuteTime(), "HH:mm"));
        familySceneTimingDO.setType(timingSceneDTO.getRepeatType());
        familySceneTimingDO.setHolidaySkipFlag(timingSceneDTO.getSkipHoliday());
        familySceneTimingDO.setEnableFlag(1);
        if (Objects.equals(FamilySceneTimingRepeatTypeEnum.getByType(timingSceneDTO.getRepeatType()), FamilySceneTimingRepeatTypeEnum.WEEK)) {
            familySceneTimingDO.setWeekday(String.join(",",DateUtils.parseWeek(timingSceneDTO.getRepeatValue().split(" "))));
        } else if (Objects.equals(FamilySceneTimingRepeatTypeEnum.getByType(timingSceneDTO.getRepeatType()), FamilySceneTimingRepeatTypeEnum.CALENDAR)) {
            String[] dateSplits = timingSceneDTO.getRepeatValue().split("-");
            familySceneTimingDO.setStartDate(DateUtils.parseLocalDate(dateSplits[0], "yyyy.MM.dd"));
            familySceneTimingDO.setEndDate(DateUtils.parseLocalDate(dateSplits[1], "yyyy.MM.dd"));
        }
        saveOrUpdate(familySceneTimingDO);
        // TODO: 添加成功后,还要做通知
        return familySceneTimingDO.getId();
    }

    @Override
    public void deleteFamilySceneById(String timingId) {
        removeById(timingId);
        // TODO: 删除成功后,还要做通知
    }

    @Autowired
    public void setFamilySceneTimingMapper(FamilySceneTimingMapper familySceneTimingMapper) {
        this.familySceneTimingMapper = familySceneTimingMapper;
    }

    @Autowired
    public void setFamilySceneService(IFamilySceneService familySceneService) {
        this.familySceneService = familySceneService;
    }

}
