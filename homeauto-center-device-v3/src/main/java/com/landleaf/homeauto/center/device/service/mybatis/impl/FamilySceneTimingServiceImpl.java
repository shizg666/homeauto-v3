package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenFamilySceneTimingBO;
import com.landleaf.homeauto.center.device.model.constant.FamilySceneTimingRepeatTypeEnum;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneTimingDO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.HouseTemplateScene;
import com.landleaf.homeauto.center.device.model.dto.TimingSceneDTO;
import com.landleaf.homeauto.center.device.model.mapper.FamilySceneTimingMapper;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilySceneTimingBO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneTimingVO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneTimingDetailVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneTimingService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateSceneService;
import com.landleaf.homeauto.center.device.util.DateUtils;
import com.landleaf.homeauto.common.constant.EscapeCharacterConst;
import com.landleaf.homeauto.common.exception.ApiException;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private IHomeAutoFamilyService familyService;
    @Autowired
    private IHouseTemplateSceneService houseTemplateSceneService;

    @Override
    public List<ScreenFamilySceneTimingBO> getTimingScenesByFamilyId(Long familyId) {
        return familySceneTimingMapper.getSceneTimingByFamilyId(familyId);
    }

    @Override
    public List<FamilySceneTimingBO> listFamilySceneTiming(Long familyId) {
        QueryWrapper<FamilySceneTimingDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("family_id", familyId);
        List<FamilySceneTimingDO> familySceneTimingDOList = list(queryWrapper);
        List<FamilySceneTimingBO> familySceneTimingBOList = new LinkedList<>();
        for (FamilySceneTimingDO familySceneTimingDO : familySceneTimingDOList) {
            Long sceneId = familySceneTimingDO.getSceneId();
            HouseTemplateScene templateScene = houseTemplateSceneService.getById(sceneId);
            if(templateScene==null){
                continue;
            }
            FamilySceneTimingBO familySceneTimingBO = new FamilySceneTimingBO();
            familySceneTimingBO.setTimingId(BeanUtil.convertLong2String(familySceneTimingDO.getId()));
            familySceneTimingBO.setExecuteSceneId(String.valueOf(familySceneTimingDO.getSceneId()));
            familySceneTimingBO.setExecuteSceneName(templateScene.getName());
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
    public void deleteTimingScene(List<Long> timingIds, Long familyId) {
        if (CollectionUtils.isEmpty(timingIds) || familyId == null) {
            return;
        }
        QueryWrapper<FamilySceneTimingDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", timingIds);
        queryWrapper.eq("family_id", familyId);
        remove(queryWrapper);
    }

    @Override
    public void updateEnabled(Long sceneTimingId, Integer enabled) {
        UpdateWrapper<FamilySceneTimingDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("enable_flag", enabled);
        updateWrapper.eq("id", sceneTimingId);
        update(updateWrapper);
    }

    /**
     * APP获取场景定时列表
     *
     * @param familyId 家庭ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneTimingVO>
     * @author wenyilu
     * @date 2020/12/28 10:41
     */
    @Override
    public List<FamilySceneTimingVO> getTimingSceneList(Long familyId) {
        List<FamilySceneTimingVO> familySceneTimingVOList = Lists.newArrayList();
        List<FamilySceneTimingBO> familySceneTimingBOList = listFamilySceneTiming(familyId);

        if (!CollectionUtils.isEmpty(familySceneTimingBOList)) {
            familySceneTimingVOList.addAll(familySceneTimingBOList.stream().map(familySceneTimingBO -> {
                FamilySceneTimingVO familySceneTimingVO = new FamilySceneTimingVO();
                familySceneTimingVO.setTimingId(familySceneTimingBO.getTimingId());
                familySceneTimingVO.setExecuteSceneId(familySceneTimingBO.getExecuteSceneId());
                familySceneTimingVO.setExecuteSceneName(familySceneTimingBO.getExecuteSceneName());
                familySceneTimingVO.setExecuteTime(DateUtils.toTimeString(familySceneTimingBO.getExecuteTime(), "HH:mm"));
                familySceneTimingVO.setEnabled(familySceneTimingBO.getEnabled());
                // 处理重复类型显示
                FamilySceneTimingRepeatTypeEnum sceneTimingRepeatTypeEnum = FamilySceneTimingRepeatTypeEnum.getByType(familySceneTimingBO.getRepeatType());
                if (Objects.equals(sceneTimingRepeatTypeEnum, FamilySceneTimingRepeatTypeEnum.NONE)) {
                    familySceneTimingVO.setWorkday(sceneTimingRepeatTypeEnum.handleWorkDay(null));
                } else if (Objects.equals(sceneTimingRepeatTypeEnum, FamilySceneTimingRepeatTypeEnum.WEEK)) {
                    String workDay = sceneTimingRepeatTypeEnum.handleWorkDay(familySceneTimingBO.getWeekday());
                    if (Objects.equals(familySceneTimingBO.getSkipHoliday(), 1)) {
                        workDay += "，跳过法定节假日";
                    }
                    familySceneTimingVO.setWorkday(workDay);
                } else {
                    String startDateString = DateUtils.toTimeString(familySceneTimingBO.getStartDate(), "yyyy.MM.dd");
                    String endDateString = DateUtils.toTimeString(familySceneTimingBO.getEndDate(), "yyyy.MM.dd");
                    String timeString = startDateString + "," + endDateString;
                    familySceneTimingVO.setWorkday(sceneTimingRepeatTypeEnum.handleWorkDay(timeString));
                }
                return familySceneTimingVO;

            }).collect(Collectors.toList()));
        }

        return familySceneTimingVOList;
    }

    /**
     * APP查看场景定时记录详情
     *
     * @param timingId 场景定时记录ID
     * @return com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneTimingVO
     * @author wenyilu
     * @date 2020/12/28 10:48
     */
    @Override
    public SceneTimingDetailVO getTimingSceneDetail(Long timingId) {
        SceneTimingDetailVO detailVO = new SceneTimingDetailVO();

        FamilySceneTimingDO familySceneTimingDO = getById(timingId);

        detailVO.setTimingId(BeanUtil.convertLong2String(familySceneTimingDO.getId()));
        detailVO.setExecuteTime(DateUtils.toTimeString(familySceneTimingDO.getExecuteTime(), "HH:mm"));
        detailVO.setRepeatType(familySceneTimingDO.getType());
        detailVO.setSkipHoliday(familySceneTimingDO.getHolidaySkipFlag());

        // 重复设置
        if (Objects.equals(FamilySceneTimingRepeatTypeEnum.getByType(familySceneTimingDO.getType()), FamilySceneTimingRepeatTypeEnum.WEEK)) {
            String weekdayInChinese = FamilySceneTimingRepeatTypeEnum.WEEK.replaceWeek(familySceneTimingDO.getWeekday().split(EscapeCharacterConst.COMMA));
            detailVO.setRepeatValue(String.join(EscapeCharacterConst.SPACE, weekdayInChinese.split(EscapeCharacterConst.COMMA)));
        } else if (Objects.equals(FamilySceneTimingRepeatTypeEnum.getByType(familySceneTimingDO.getType()), FamilySceneTimingRepeatTypeEnum.CALENDAR)) {
            String startDateString = DateUtils.toTimeString(familySceneTimingDO.getStartDate(), "yyyy.MM.dd");
            String endDateString = DateUtils.toTimeString(familySceneTimingDO.getEndDate(), "yyyy.MM.dd");
            detailVO.setRepeatValue(startDateString + "-" + endDateString);
        }
        Long familyId = familySceneTimingDO.getFamilyId();
        HomeAutoFamilyDO familyDO = familyService.getById(familyId);
        List<HouseTemplateScene> scenesByTemplate = houseTemplateSceneService.getScenesByTemplate(familyDO.getTemplateId());
        // 场景设置
        List<FamilySceneVO> familySceneVOList = new LinkedList<>();
        for (HouseTemplateScene scene : scenesByTemplate) {
            FamilySceneVO familySceneVO = new FamilySceneVO();
//            familySceneVO.setSceneId(scene.getId());
            familySceneVO.setSceneName(scene.getName());
            familySceneVO.setSceneIcon(scene.getIcon());
            familySceneVO.setChecked(Objects.equals(scene.getId(), familySceneTimingDO.getSceneId()) ? 1 : 0);
            familySceneVOList.add(familySceneVO);
        }
        detailVO.setScenes(familySceneVOList);
        detailVO.setSceneId(String.valueOf(familySceneTimingDO.getSceneId()));
        detailVO.setEnabled(familySceneTimingDO.getEnableFlag());
        return detailVO;
    }

    @Override
    public boolean saveTimingScene(TimingSceneDTO timingSceneDTO) {
        if (org.springframework.util.StringUtils.isEmpty(timingSceneDTO.getExecuteTime())) {
            throw new ApiException("执行时间不可为空");
        } else if (org.springframework.util.StringUtils.isEmpty(timingSceneDTO.getFamilyId())) {
            throw new ApiException("家庭ID不可为空");
        } else if (org.springframework.util.StringUtils.isEmpty(timingSceneDTO.getSceneId())) {
            throw new ApiException("场景ID不可为空");
        } else if (Objects.isNull(timingSceneDTO.getRepeatType())) {
            throw new ApiException("重复类型不可为空");
        }
        FamilySceneTimingDO familySceneTimingDO = new FamilySceneTimingDO();
        familySceneTimingDO.setId(BeanUtil.convertString2Long(timingSceneDTO.getTimingId()));
        familySceneTimingDO.setFamilyId(BeanUtil.convertString2Long(timingSceneDTO.getFamilyId()));
        familySceneTimingDO.setSceneId(Long.parseLong(timingSceneDTO.getSceneId()));
        familySceneTimingDO.setExecuteTime(DateUtils.parseLocalTime(timingSceneDTO.getExecuteTime(), "HH:mm"));
        familySceneTimingDO.setType(timingSceneDTO.getRepeatType());
        familySceneTimingDO.setHolidaySkipFlag(timingSceneDTO.getSkipHoliday());
        familySceneTimingDO.setEnableFlag(1);
        if (Objects.equals(FamilySceneTimingRepeatTypeEnum.getByType(timingSceneDTO.getRepeatType()), FamilySceneTimingRepeatTypeEnum.WEEK)) {
            familySceneTimingDO.setWeekday(String.join(EscapeCharacterConst.COMMA, DateUtils.parseWeek(timingSceneDTO.getRepeatValue().split(EscapeCharacterConst.SPACE))));
        } else if (Objects.equals(FamilySceneTimingRepeatTypeEnum.getByType(timingSceneDTO.getRepeatType()), FamilySceneTimingRepeatTypeEnum.CALENDAR)) {
            String[] dateSplits = timingSceneDTO.getRepeatValue().split("-");
            familySceneTimingDO.setStartDate(DateUtils.parseLocalDate(dateSplits[0], "yyyy.MM.dd"));
            familySceneTimingDO.setEndDate(DateUtils.parseLocalDate(dateSplits[1], "yyyy.MM.dd"));
        }
        return saveOrUpdate(familySceneTimingDO);
    }


}
