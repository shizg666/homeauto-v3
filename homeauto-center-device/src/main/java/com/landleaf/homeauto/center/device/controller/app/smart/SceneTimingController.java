package com.landleaf.homeauto.center.device.controller.app.smart;

import com.landleaf.homeauto.center.device.model.constant.FamilySceneTimingRepeatTypeEnum;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneDO;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneTimingDO;
import com.landleaf.homeauto.center.device.model.dto.TimingSceneDTO;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilySceneTimingBO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneTimingVO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneTimingService;
import com.landleaf.homeauto.center.device.util.DateUtils;
import com.landleaf.homeauto.common.constant.EscapeCharacterConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
import com.landleaf.homeauto.common.exception.ApiException;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author Yujiumin
 * @version 2020/8/15
 */
@Slf4j
@RestController
@RequestMapping("/app/smart/scene/timing")
@Api(value = "定时场景控制器", tags = "户式化APP定时场景接口")
public class SceneTimingController extends BaseController {

    @Autowired
    private IFamilySceneTimingService familySceneTimingService;

    @Autowired
    private IFamilySceneService familySceneService;

    /**
     * 查看定时场景列表
     *
     * @param familyId 家庭ID
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("查看定时场景列表")
    public Response<List<FamilySceneTimingVO>> getTimingSceneList(@RequestParam String familyId) {
        List<FamilySceneTimingBO> familySceneTimingBOList = familySceneTimingService.listFamilySceneTiming(familyId);
        List<FamilySceneTimingVO> familySceneTimingVOList = new LinkedList<>();
        for (FamilySceneTimingBO familySceneTimingBO : familySceneTimingBOList) {
            FamilySceneTimingVO familySceneTimingVO = new FamilySceneTimingVO();
            familySceneTimingVO.setTimingId(familySceneTimingBO.getTimingSceneId());
            familySceneTimingVO.setSceneName(familySceneTimingBO.getExecuteSceneName());
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
            familySceneTimingVOList.add(familySceneTimingVO);
        }
        return returnSuccess(familySceneTimingVOList);
    }

    /**
     * 查看定时场景内容
     *
     * @param timingId 定时场景ID
     * @return 定时场景内容
     */
    @GetMapping("/detail")
    @ApiOperation("查看定时场景内容")
    public Response<FamilySceneTimingVO> getTimingSceneDetail(@RequestParam String timingId) {
        FamilySceneTimingDO familySceneTimingDO = familySceneTimingService.getById(timingId);
        FamilySceneTimingVO familySceneTimingVO = new FamilySceneTimingVO();
        familySceneTimingVO.setTimingId(familySceneTimingDO.getId());
        familySceneTimingVO.setExecuteTime(DateUtils.toTimeString(familySceneTimingDO.getExecuteTime(), "HH:mm"));
        familySceneTimingVO.setRepeatType(familySceneTimingDO.getType());
        familySceneTimingVO.setSkipHoliday(familySceneTimingDO.getHolidaySkipFlag());

        // 重复设置
        if (Objects.equals(FamilySceneTimingRepeatTypeEnum.getByType(familySceneTimingDO.getType()), FamilySceneTimingRepeatTypeEnum.WEEK)) {
            String weekdayInChinese = FamilySceneTimingRepeatTypeEnum.WEEK.replaceWeek(familySceneTimingDO.getWeekday().split(EscapeCharacterConst.COMMA));
            familySceneTimingVO.setRepeatValue(String.join(EscapeCharacterConst.SPACE, weekdayInChinese.split(EscapeCharacterConst.COMMA)));
        } else if (Objects.equals(FamilySceneTimingRepeatTypeEnum.getByType(familySceneTimingDO.getType()), FamilySceneTimingRepeatTypeEnum.CALENDAR)) {
            String startDateString = DateUtils.toTimeString(familySceneTimingDO.getStartDate(), "yyyy.MM.dd");
            String endDateString = DateUtils.toTimeString(familySceneTimingDO.getEndDate(), "yyyy.MM.dd");
            familySceneTimingVO.setRepeatValue(startDateString + "-" + endDateString);
        }

        // 场景设置
        List<FamilySceneDO> familySceneDOList = familySceneService.getFamilyScenesBySceneId(familySceneTimingDO.getSceneId());
        List<FamilySceneVO> familySceneVOList = new LinkedList<>();
        for (FamilySceneDO familySceneDO : familySceneDOList) {
            FamilySceneVO familySceneVO = new FamilySceneVO();
            familySceneVO.setSceneId(familySceneDO.getId());
            familySceneVO.setSceneName(familySceneDO.getName());
            familySceneVO.setSceneIcon(familySceneDO.getIcon());
            familySceneVO.setChecked(Objects.equals(familySceneDO.getId(), familySceneTimingDO.getSceneId()) ? 1 : 0);
            familySceneVOList.add(familySceneVO);
        }
        familySceneTimingVO.setScenes(familySceneVOList);
        return returnSuccess(familySceneTimingVO);
    }

    /**
     * 添加或修改定时场景
     *
     * @param timingSceneDTO 定时场景信息
     * @return 操作结果
     */
    @PostMapping("/save")
    @ApiOperation("添加定时场景")
    public Response<Boolean> save(@RequestBody TimingSceneDTO timingSceneDTO) {
        if (StringUtils.isEmpty(timingSceneDTO.getExecuteTime())) {
            throw new ApiException("执行时间不可为空");
        } else if (StringUtils.isEmpty(timingSceneDTO.getFamilyId())) {
            throw new ApiException("家庭ID不可为空");
        } else if (StringUtils.isEmpty(timingSceneDTO.getSceneId())) {
            throw new ApiException("场景ID不可为空");
        } else if (Objects.isNull(timingSceneDTO.getRepeatType())) {
            throw new ApiException("重复类型不可为空");
        }
        FamilySceneTimingDO familySceneTimingDO = new FamilySceneTimingDO();
        familySceneTimingDO.setId(timingSceneDTO.getTimingId());
        familySceneTimingDO.setFamilyId(timingSceneDTO.getFamilyId());
        familySceneTimingDO.setSceneId(timingSceneDTO.getSceneId());
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
        familySceneTimingService.saveOrUpdate(familySceneTimingDO);

        // 通知大屏定时配置更新
        familySceneService.notifyConfigUpdate(timingSceneDTO.getFamilyId(), ContactScreenConfigUpdateTypeEnum.SCENE_TIMING);
        return returnSuccess(familySceneTimingDO.getId());
    }

    /**
     * 删除定时场景
     *
     * @param timingSceneId 定时场景ID
     * @return 操作结果
     */
    @PostMapping("/delete/{timingSceneId}")
    @ApiOperation("删除定时场景")
    public Response<Boolean> deleteFamilySceneTiming(@PathVariable String timingSceneId) {
        FamilySceneTimingDO familySceneTimingDO = familySceneTimingService.getById(timingSceneId);
        familySceneTimingService.removeById(timingSceneId);

        // 通知大屏定时场景配置更新
        familySceneService.notifyConfigUpdate(familySceneTimingDO.getFamilyId(), ContactScreenConfigUpdateTypeEnum.SCENE_TIMING);
        return returnSuccess(true);
    }

    /**
     * 启用(禁用)定时场景
     *
     * @param sceneTimingId 定时场景ID
     * @return 操作结果
     */
    @PostMapping("/switch/toggle/{sceneTimingId}")
    @ApiOperation("定时场景启用(禁用)接口")
    public Response<Boolean> enableSceneTiming(@PathVariable String sceneTimingId) {
        FamilySceneTimingDO familySceneTimingDO = familySceneTimingService.getById(sceneTimingId);
        int targetEnabled = (familySceneTimingDO.getEnableFlag() + 1) % 2;
        familySceneTimingService.updateEnabled(sceneTimingId, targetEnabled);

        // 通知大屏定时场景配置更新
        familySceneService.notifyConfigUpdate(familySceneTimingDO.getFamilyId(), ContactScreenConfigUpdateTypeEnum.SCENE_TIMING);
        return returnSuccess(true);
    }

}
