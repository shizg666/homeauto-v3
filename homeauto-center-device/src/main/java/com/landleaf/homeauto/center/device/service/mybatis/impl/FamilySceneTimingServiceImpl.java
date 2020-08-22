package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.bo.FamilySceneTimingBO;
import com.landleaf.homeauto.center.device.model.constant.FamilySceneTimingRepeatTypeEnum;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneDO;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneTimingDO;
import com.landleaf.homeauto.center.device.model.dto.FamilySceneTimingDTO;
import com.landleaf.homeauto.center.device.model.mapper.FamilySceneTimingMapper;
import com.landleaf.homeauto.center.device.model.vo.TimingSceneDetailVO;
import com.landleaf.homeauto.center.device.model.vo.TimingSceneVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneTimingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
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
    public List<TimingSceneVO> getTimingScenesByFamilyId(String familyId) {
        List<FamilySceneTimingBO> familySceneTimingDOList = familySceneTimingMapper.getSceneTimingByFamilyId(familyId);
        List<TimingSceneVO> timingSceneVOList = new LinkedList<>();
        for (FamilySceneTimingBO familySceneTimingBO : familySceneTimingDOList) {
            TimingSceneVO timingSceneVO = new TimingSceneVO();
            timingSceneVO.setTimingId(familySceneTimingBO.getTimingId());
            timingSceneVO.setSceneName(familySceneTimingBO.getSceneName());
            timingSceneVO.setTime(getTimeString(familySceneTimingBO.getExecuteTime(), "HH:mm"));
            timingSceneVO.setEnabled(familySceneTimingBO.getEnabled());

            // 处理重复类型显示
            FamilySceneTimingRepeatTypeEnum sceneTimingRepeatTypeEnum = FamilySceneTimingRepeatTypeEnum.getByType(familySceneTimingBO.getType());
            if (Objects.equals(sceneTimingRepeatTypeEnum, FamilySceneTimingRepeatTypeEnum.NONE)) {
                timingSceneVO.setWorkday(sceneTimingRepeatTypeEnum.handleWorkDay(null));
            } else if (Objects.equals(sceneTimingRepeatTypeEnum, FamilySceneTimingRepeatTypeEnum.WEEK)) {
                timingSceneVO.setWorkday(sceneTimingRepeatTypeEnum.handleWorkDay(familySceneTimingBO.getWeekday()));
            } else {
                String startDateString = getTimeString(familySceneTimingBO.getStartDate(), "yyyy.MM.dd");
                String endDateString = getTimeString(familySceneTimingBO.getEndDate(), "yyyy.MM.dd");
                String timeString = startDateString + "," + endDateString;
                timingSceneVO.setWorkday(sceneTimingRepeatTypeEnum.handleWorkDay(timeString));
            }
            timingSceneVOList.add(timingSceneVO);
        }
        return timingSceneVOList;
    }

    @Override
    public TimingSceneDetailVO getTimingSceneDetailByTimingId(String timingId) {
        FamilySceneTimingDO familySceneTimingDO = baseMapper.selectById(timingId);
        TimingSceneDetailVO timingSceneDetailVO = new TimingSceneDetailVO();
        timingSceneDetailVO.setTimingId(familySceneTimingDO.getId());
        timingSceneDetailVO.setExecTime(getTimeString(familySceneTimingDO.getExecuteTime(), "HH:mm"));
        timingSceneDetailVO.setRepeatType(familySceneTimingDO.getType());
        timingSceneDetailVO.setSkipHoliday(familySceneTimingDO.getHolidaySkipFlag());

        // 重复设置
        if (Objects.equals(FamilySceneTimingRepeatTypeEnum.getByType(familySceneTimingDO.getType()), FamilySceneTimingRepeatTypeEnum.WEEK)) {
            timingSceneDetailVO.setRepeatValue(FamilySceneTimingRepeatTypeEnum.WEEK.replaceWeek(familySceneTimingDO.getWeekday().split(",")));
        } else if (Objects.equals(FamilySceneTimingRepeatTypeEnum.getByType(familySceneTimingDO.getType()), FamilySceneTimingRepeatTypeEnum.CALENDAR)) {
            String startDateString = getTimeString(familySceneTimingDO.getStartDate(), "yyyy.MM.dd");
            String endDateString = getTimeString(familySceneTimingDO.getEndDate(), "yyyy.MM.dd");
            timingSceneDetailVO.setRepeatValue(startDateString + "," + endDateString);
        }

        // 场景设置
        List<FamilySceneDO> familySceneDOList = familySceneService.getFamilyScenesBySceneId(familySceneTimingDO.getSceneId());
        List<TimingSceneDetailVO.SceneVO> timingSceneVOList = new LinkedList<>();
        for (FamilySceneDO familySceneDO : familySceneDOList) {
            TimingSceneDetailVO.SceneVO sceneVO = new TimingSceneDetailVO.SceneVO();
            sceneVO.setSceneId(familySceneDO.getId());
            sceneVO.setSceneName(familySceneDO.getName());
            sceneVO.setSceneIcon(familySceneDO.getIcon());
            sceneVO.setChecked(Objects.equals(familySceneDO.getId(), familySceneTimingDO.getSceneId()) ? 1 : 0);
            timingSceneVOList.add(sceneVO);
        }
        timingSceneDetailVO.setScenes(timingSceneVOList);
        return timingSceneDetailVO;
    }

    @Override
    public String insertFamilySceneTiming(FamilySceneTimingDTO familySceneTimingDTO) {
        FamilySceneTimingDO familySceneTimingDO = new FamilySceneTimingDO();
        familySceneTimingDO.setSceneId(familySceneTimingDTO.getSceneId());
        familySceneTimingDO.setExecuteTime(familySceneTimingDTO.getExecuteTime());
        familySceneTimingDO.setType(familySceneTimingDTO.getType());
        familySceneTimingDO.setHolidaySkipFlag(familySceneTimingDTO.getSkipHoliday());
        familySceneTimingDO.setEnableFlag(1);
        if (Objects.equals(FamilySceneTimingRepeatTypeEnum.getByType(familySceneTimingDTO.getType()), FamilySceneTimingRepeatTypeEnum.WEEK)) {
            familySceneTimingDO.setWeekday(familySceneTimingDTO.getWeekday());
        } else if (Objects.equals(FamilySceneTimingRepeatTypeEnum.getByType(familySceneTimingDTO.getType()), FamilySceneTimingRepeatTypeEnum.CALENDAR)) {
            familySceneTimingDO.setStartDate(familySceneTimingDTO.getStartDate());
            familySceneTimingDO.setEndDate(familySceneTimingDTO.getEndDate());
        }
        save(familySceneTimingDO);
        // TODO: 添加成功后,还要做通知
        return familySceneTimingDO.getId();
    }

    @Override
    public void deleteFamilySceneById(String timingId) {
        removeById(timingId);
    }

    @Override
    public void updateFamilySceneById(FamilySceneTimingDTO familySceneTimingDTO) {
        FamilySceneTimingDO familySceneTimingDO = new FamilySceneTimingDO();
        familySceneTimingDO.setId(familySceneTimingDTO.getTimingId());
        familySceneTimingDO.setSceneId(familySceneTimingDTO.getSceneId());
        familySceneTimingDO.setExecuteTime(familySceneTimingDTO.getExecuteTime());
        familySceneTimingDO.setType(familySceneTimingDTO.getType());
        familySceneTimingDO.setHolidaySkipFlag(familySceneTimingDTO.getSkipHoliday());
        familySceneTimingDO.setEnableFlag(1);
        if (Objects.equals(FamilySceneTimingRepeatTypeEnum.getByType(familySceneTimingDTO.getType()), FamilySceneTimingRepeatTypeEnum.WEEK)) {
            familySceneTimingDO.setWeekday(familySceneTimingDTO.getWeekday());
        } else if (Objects.equals(FamilySceneTimingRepeatTypeEnum.getByType(familySceneTimingDTO.getType()), FamilySceneTimingRepeatTypeEnum.CALENDAR)) {
            familySceneTimingDO.setStartDate(familySceneTimingDTO.getStartDate());
            familySceneTimingDO.setEndDate(familySceneTimingDTO.getEndDate());
        }
        updateById(familySceneTimingDO);
        // TODO: 添加成功后,还要做通知
    }

    @Autowired
    public void setFamilySceneTimingMapper(FamilySceneTimingMapper familySceneTimingMapper) {
        this.familySceneTimingMapper = familySceneTimingMapper;
    }

    @Autowired
    public void setFamilySceneService(IFamilySceneService familySceneService) {
        this.familySceneService = familySceneService;
    }

    private String getTimeString(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern, Locale.CHINA);
        return localDateTime.format(dateTimeFormatter);
    }

    private String getTimeString(LocalDate localDate, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern, Locale.CHINA);
        return localDate.format(dateTimeFormatter);
    }

    private String getTimeString(LocalTime localTime, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern, Locale.CHINA);
        return localTime.format(dateTimeFormatter);
    }
}
