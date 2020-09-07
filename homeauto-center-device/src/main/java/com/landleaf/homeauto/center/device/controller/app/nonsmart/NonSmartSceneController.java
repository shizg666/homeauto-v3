package com.landleaf.homeauto.center.device.controller.app.nonsmart;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.landleaf.homeauto.center.device.enums.CategoryEnum;
import com.landleaf.homeauto.center.device.enums.SceneEnum;
import com.landleaf.homeauto.center.device.model.bo.FamilySceneTimingBO;
import com.landleaf.homeauto.center.device.model.bo.HvacSceneConfigActionBO;
import com.landleaf.homeauto.center.device.model.bo.SceneSimpleBO;
import com.landleaf.homeauto.center.device.model.constant.FamilySceneTimingRepeatTypeEnum;
import com.landleaf.homeauto.center.device.model.domain.*;
import com.landleaf.homeauto.center.device.model.dto.NonSmartCustomSceneDTO;
import com.landleaf.homeauto.center.device.model.dto.SceneActionDTO;
import com.landleaf.homeauto.center.device.model.dto.TimingSceneDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.*;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.center.device.util.DateUtils;
import com.landleaf.homeauto.common.constant.EscapeCharacterConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterConfigUpdateAckDTO;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 场景控制器
 *
 * @author Yujiumin
 * @version 2020/8/31
 */
@Slf4j
@RestController
@RequestMapping("/app/non-smart/scene")
@Api(tags = "自由方舟APP场景接口")
public class NonSmartSceneController extends BaseController {

    @Autowired
    private IFamilySceneService familySceneService;

    @Autowired
    private IFamilyCommonSceneService familyCommonSceneService;

    @Autowired
    private IFamilySceneTimingService familySceneTimingService;

    @Autowired
    private IFamilyDeviceService familyDeviceService;

    @Autowired
    private IFamilyRoomService familyRoomService;

    @Autowired
    private IFamilySceneHvacConfigService familySceneHvacConfigService;

    @Autowired
    private IFamilySceneHvacConfigActionService familySceneHvacConfigActionService;

    @Autowired
    private IFamilySceneHvacConfigActionPanelService familySceneHvacConfigActionPanelService;

    @PostMapping("/save")
    @ApiOperation("添加或编辑场景")
    @Transactional(rollbackFor = Exception.class)
    public Response<?> saveOrUpdate(@RequestBody NonSmartCustomSceneDTO customSceneDTO) {
        // 1. 添加场景
        FamilySceneDO familySceneDO = new FamilySceneDO();
        familySceneDO.setName(customSceneDTO.getSceneName());
        familySceneDO.setFamilyId(customSceneDTO.getFamilyId());
        familySceneDO.setType(SceneEnum.WHOLE_HOUSE_SCENE.getType());
        familySceneDO.setIcon(customSceneDTO.getPicUrl());
        familySceneDO.setDefaultFlag(0);
        familySceneDO.setDefaultFlagScreen(1);
        familySceneDO.setHvacFlag(1);
        familySceneDO.setUpdateFlagApp(1);
        familySceneService.saveOrUpdate(familySceneDO);

        if (Objects.equals(customSceneDTO.getCommonUse(), 1)) {
            //// 1.1-1 设为常用场景
            FamilyCommonSceneDO familyCommonSceneDO = new FamilyCommonSceneDO();
            familyCommonSceneDO.setFamilyId(customSceneDTO.getFamilyId());
            familyCommonSceneDO.setSceneId(familySceneDO.getId());
            familyCommonSceneDO.setSortNo(0);
            familyCommonSceneService.save(familyCommonSceneDO);
        } else if (!Objects.isNull(customSceneDTO.getSceneId())) {
            //// 1.1-2 取消设为常用场景
            familyCommonSceneService.deleteFamilySceneCommonScene(customSceneDTO.getFamilyId(), customSceneDTO.getSceneId());
        }

        // 2. 添加场景暖通配置
        FamilyDeviceDO hvacDevice = familyDeviceService.getFamilyDevice(customSceneDTO.getFamilyId(), CategoryEnum.HVAC);
        FamilySceneHvacConfig familySceneHvacConfig = new FamilySceneHvacConfig();
        familySceneHvacConfig.setId(customSceneDTO.getSceneConfigId());
        familySceneHvacConfig.setFamilyId(customSceneDTO.getFamilyId());
        familySceneHvacConfig.setSceneId(familySceneDO.getId());
        familySceneHvacConfig.setDeviceSn(hvacDevice.getSn());
        familySceneHvacConfig.setSwitchCode("switch");
        familySceneHvacConfig.setSwitchVal("on");
        familySceneHvacConfigService.saveOrUpdate(familySceneHvacConfig);

        // 3. 添加暖通模式配置
        for (SceneActionDTO sceneActionDTO : customSceneDTO.getSceneActions()) {
            FamilySceneHvacConfigAction familySceneHvacConfigAction = new FamilySceneHvacConfigAction();
            familySceneHvacConfigAction.setId(sceneActionDTO.getConfigId());
            familySceneHvacConfigAction.setModeCode("mode");
            familySceneHvacConfigAction.setModeVal(sceneActionDTO.getWorkMode());
            familySceneHvacConfigAction.setWindCode("air_volume");
            familySceneHvacConfigAction.setWindVal(sceneActionDTO.getAirSpeed());
            familySceneHvacConfigAction.setRoomFlag(0);
            familySceneHvacConfigAction.setHvacConfigId(familySceneHvacConfig.getId());
            familySceneHvacConfigAction.setSwitchCode("switch");
            familySceneHvacConfigAction.setSwitchVal("on");
            familySceneHvacConfigActionService.saveOrUpdate(familySceneHvacConfigAction);

            // 4. 添加暖通模式分室配置
            for (SceneActionDTO.RoomParam roomParam : sceneActionDTO.getRoomParams()) {
                FamilySceneHvacConfigActionPanel familySceneHvacConfigActionPanel = new FamilySceneHvacConfigActionPanel();
                familySceneHvacConfigActionPanel.setId(roomParam.getRoomConfigId());
                familySceneHvacConfigActionPanel.setDeviceSn(familyDeviceService.getRoomPanel(roomParam.getRoomId()).getSn());
                familySceneHvacConfigActionPanel.setSwitchCode("switch");
                familySceneHvacConfigActionPanel.setSwitchVal("on");
                familySceneHvacConfigActionPanel.setTemperatureCode("setting_temperature");
                familySceneHvacConfigActionPanel.setTemperatureVal(String.valueOf(roomParam.getTemperature()));
                familySceneHvacConfigActionPanel.setHvacActionId(familySceneHvacConfigAction.getId());
                familySceneHvacConfigActionPanel.setFamilyId(customSceneDTO.getFamilyId());
                familySceneHvacConfigActionPanelService.save(familySceneHvacConfigActionPanel);
            }
        }

        return returnSuccess();
        // 场景更新通知
//        AdapterConfigUpdateAckDTO adapterConfigUpdateAckDTO = familySceneService.notifyConfigUpdate(customSceneDTO.getFamilyId(), ContactScreenConfigUpdateTypeEnum.SCENE);
//        if (Objects.equals(adapterConfigUpdateAckDTO.getCode(), 200)) {
//            return returnSuccess();
//        }
//        throw new BusinessException(adapterConfigUpdateAckDTO.getMessage());
    }

    @PostMapping("/delete/{sceneId}")
    @ApiOperation("删除场景")
    @Transactional(rollbackFor = Exception.class)
    public Response<?> deleteScene(@PathVariable String sceneId) {
        FamilySceneDO familySceneDO = familySceneService.getById(sceneId);

        QueryWrapper<FamilySceneHvacConfig> configQueryWrapper = new QueryWrapper<>();
        configQueryWrapper.eq("scene_id", sceneId);
        FamilySceneHvacConfig config = familySceneHvacConfigService.getOne(configQueryWrapper);

        QueryWrapper<FamilySceneHvacConfigAction> configActionQueryWrapper = new QueryWrapper<>();
        configActionQueryWrapper.eq("hvac_config_id", config.getId());
        List<FamilySceneHvacConfigAction> configActionList = familySceneHvacConfigActionService.list(configActionQueryWrapper);

        // 删除场景暖通模式分室配置
        for (FamilySceneHvacConfigAction familySceneHvacConfigAction : configActionList) {
            QueryWrapper<FamilySceneHvacConfigActionPanel> configActionPanelQueryWrapper = new QueryWrapper<>();
            configActionPanelQueryWrapper.eq("hvac_action_id", familySceneHvacConfigAction.getId());
            familySceneHvacConfigActionPanelService.remove(configActionPanelQueryWrapper);
        }

        // 删除场景模式配置
        familySceneHvacConfigActionService.remove(configActionQueryWrapper);

        // 删除场景配置
        familySceneHvacConfigService.remove(configQueryWrapper);

        // 删除场景
        familySceneService.removeById(sceneId);
        return returnSuccess();
//        AdapterConfigUpdateAckDTO adapterConfigUpdateAckDTO = familySceneService.notifyConfigUpdate(familySceneDO.getFamilyId(), ContactScreenConfigUpdateTypeEnum.SCENE);
//        if (Objects.equals(adapterConfigUpdateAckDTO.getCode(), 200)) {
//            return returnSuccess();
//        }
//        throw new BusinessException(adapterConfigUpdateAckDTO.getMessage());
    }

    @GetMapping("/list/{familyId}")
    @ApiOperation("查看场景列表")
    public Response<List<SceneVO>> getFamilyWholeHouseScenes(@PathVariable String familyId) {
        QueryWrapper<FamilySceneDO> familySceneQueryWrapper = new QueryWrapper<>();
        familySceneQueryWrapper.eq("type", SceneEnum.WHOLE_HOUSE_SCENE.getType());
        familySceneQueryWrapper.eq("family_id", familyId);
        List<FamilySceneDO> familyScenePoList = familySceneService.list(familySceneQueryWrapper);
        List<SceneVO> familySceneVOList = new LinkedList<>();
        for (FamilySceneDO familySceneDO : familyScenePoList) {
            SceneVO familySceneVO = new SceneVO();
            familySceneVO.setSceneId(familySceneDO.getId());
            familySceneVO.setSceneName(familySceneDO.getName());
            familySceneVO.setSceneIcon(familySceneDO.getIcon());
            familySceneVO.setIndex(0);
            familySceneVOList.add(familySceneVO);
        }
        return returnSuccess(familySceneVOList);
    }

    @GetMapping("/detail/{sceneId}")
    @ApiOperation("查看场景详情")
    public Response<NonSmartSceneDetailVO> viewScene(@PathVariable String sceneId) {
        // 1. 查场景
        NonSmartSceneDetailVO sceneDetailVO = new NonSmartSceneDetailVO();
        FamilySceneDO familySceneDO = familySceneService.getById(sceneId);

        // 2. 查场景配置
        QueryWrapper<FamilySceneHvacConfig> configQueryWrapper = new QueryWrapper<>();
        configQueryWrapper.eq("scene_id", sceneId);
        FamilySceneHvacConfig familySceneHvacConfig = familySceneHvacConfigService.getOne(configQueryWrapper, true);

        // 3. 查场景模式
        QueryWrapper<FamilySceneHvacConfigAction> configActionQueryWrapper = new QueryWrapper<>();
        configActionQueryWrapper.eq("hvac_config_id", familySceneHvacConfig.getId());
        List<FamilySceneHvacConfigAction> configActionList = familySceneHvacConfigActionService.list(configActionQueryWrapper);

        List<SceneActionVO> sceneActionVOList = new LinkedList<>();
        for (FamilySceneHvacConfigAction configAction : configActionList) {
            // 4. 查场景模式配置
            QueryWrapper<FamilySceneHvacConfigActionPanel> configActionPanelQueryWrapper = new QueryWrapper<>();
            configActionPanelQueryWrapper.eq("hvac_action_id", configAction.getId());
            List<FamilySceneHvacConfigActionPanel> configActionPanelList = familySceneHvacConfigActionPanelService.list(configActionPanelQueryWrapper);

            List<SceneActionVO.RoomParam> roomParamList = new LinkedList<>();
            for (FamilySceneHvacConfigActionPanel familySceneHvacConfigActionPanel : configActionPanelList) {
                QueryWrapper<FamilyDeviceDO> deviceQueryWrapper = new QueryWrapper<>();
                deviceQueryWrapper.eq("sn", familySceneHvacConfigActionPanel.getDeviceSn());
                FamilyDeviceDO deviceDO = familyDeviceService.getOne(deviceQueryWrapper);
                String roomName = familyRoomService.getById(deviceDO.getRoomId()).getName();

                SceneActionVO.RoomParam roomParam = new SceneActionVO.RoomParam();
                roomParam.setRoomConfigId(familySceneHvacConfigActionPanel.getId());
                roomParam.setRoomName(roomName);
                roomParam.setTemperature(familySceneHvacConfigActionPanel.getTemperatureVal());
                roomParamList.add(roomParam);
            }

            SceneActionVO sceneActionVO = new SceneActionVO();
            sceneActionVO.setConfigId(configAction.getHvacConfigId());
            sceneActionVO.setWorkMode(configAction.getModeVal());
            sceneActionVO.setAirSpeed(configAction.getWindVal());
            sceneActionVO.setRoomParams(roomParamList);
            sceneActionVOList.add(sceneActionVO);
        }

        sceneDetailVO.setSceneConfigId(familySceneHvacConfig.getId());
        sceneDetailVO.setSceneName(familySceneDO.getName());
        sceneDetailVO.setPicUrl(familySceneDO.getIcon());
        sceneDetailVO.setCommonUse(familyCommonSceneService.isExist(familySceneDO.getFamilyId(), sceneId) ? 1 : 0);
        sceneDetailVO.setSceneActions(sceneActionVOList);
        return returnSuccess(sceneDetailVO);
    }

    //-------------------------------------------- 场景定时接口 --------------------------------------------//

    @PostMapping("/timing/save")
    @ApiOperation("添加或编辑定时场景")
    public Response<String> addFamilySceneTiming(@RequestBody TimingSceneDTO timingSceneDTO) {
        FamilySceneTimingDO familySceneTimingDO = new FamilySceneTimingDO();
        familySceneTimingDO.setId(timingSceneDTO.getTimingId());
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
        return returnSuccess();
//        FamilySceneDO familySceneDO = familySceneService.getById(timingSceneDTO.getSceneId());
//        AdapterConfigUpdateAckDTO adapterConfigUpdateAckDTO = familySceneService.notifyConfigUpdate(familySceneDO.getFamilyId(), ContactScreenConfigUpdateTypeEnum.SCENE_TIMING);
//        if (Objects.equals(adapterConfigUpdateAckDTO.getCode(), 200)) {
//            return returnSuccess(familySceneTimingDO.getId());
//        }
//        throw new BusinessException(adapterConfigUpdateAckDTO.getMessage());
    }

    @PostMapping("/timing/delete/{timingId}")
    @ApiOperation("删除定时场景")
    public Response<?> deleteFamilySceneTiming(@PathVariable String timingId) {
        FamilySceneTimingDO familySceneTimingDO = familySceneTimingService.getById(timingId);
        familySceneTimingService.removeById(timingId);
        return returnSuccess();
//        AdapterConfigUpdateAckDTO adapterConfigUpdateAckDTO = familySceneService.notifyConfigUpdate(familySceneTimingDO.getFamilyId(), ContactScreenConfigUpdateTypeEnum.SCENE_TIMING);
//        if (Objects.equals(adapterConfigUpdateAckDTO.getCode(), 200)) {
//            return returnSuccess();
//        }
//        throw new BusinessException(adapterConfigUpdateAckDTO.getMessage());
    }

    @GetMapping("/timing/list/{familyId}")
    @ApiOperation("查看定时场景列表")
    public Response<List<SceneTimingVO>> getTimingSceneList(@PathVariable String familyId) {
        List<FamilySceneTimingBO> familySceneTimingBOList = familySceneTimingService.getTimingScenesByFamilyId(familyId);
        List<SceneTimingVO> sceneTimingVOList = new LinkedList<>();
        for (FamilySceneTimingBO familySceneTimingBO : familySceneTimingBOList) {
            SceneTimingVO sceneTimingVO = new SceneTimingVO();
            sceneTimingVO.setTimingId(familySceneTimingBO.getTimingId());
            sceneTimingVO.setSceneName(familySceneTimingBO.getSceneName());
            sceneTimingVO.setExecuteTime(DateUtils.toTimeString(familySceneTimingBO.getExecuteTime(), "HH:mm"));
            sceneTimingVO.setEnabled(familySceneTimingBO.getEnabled());

            // 处理重复类型显示
            FamilySceneTimingRepeatTypeEnum sceneTimingRepeatTypeEnum = FamilySceneTimingRepeatTypeEnum.getByType(familySceneTimingBO.getType());
            if (Objects.equals(sceneTimingRepeatTypeEnum, FamilySceneTimingRepeatTypeEnum.NONE)) {
                sceneTimingVO.setWorkday("单次生效");
            } else if (Objects.equals(sceneTimingRepeatTypeEnum, FamilySceneTimingRepeatTypeEnum.WEEK)) {
                String workDay = sceneTimingRepeatTypeEnum.handleWorkDay(familySceneTimingBO.getWeekday());
                sceneTimingVO.setWorkday(workDay);
            } else {
                String startDateString = DateUtils.toTimeString(familySceneTimingBO.getStartDate(), "yyyy.MM.dd");
                String endDateString = DateUtils.toTimeString(familySceneTimingBO.getEndDate(), "yyyy.MM.dd");
                String timeString = startDateString + "," + endDateString;
                sceneTimingVO.setWorkday(sceneTimingRepeatTypeEnum.handleWorkDay(timeString));
            }
            sceneTimingVOList.add(sceneTimingVO);
        }
        return returnSuccess(sceneTimingVOList);
    }

    @GetMapping("/timing/detail/{timingId}")
    @ApiOperation("查看定时场景内容")
    public Response<SceneTimingDetailVO> getTimingSceneDetail(@PathVariable String timingId) {
        FamilySceneTimingDO familySceneTimingDO = familySceneTimingService.getById(timingId);
        SceneTimingDetailVO timingSceneDetailVO = new SceneTimingDetailVO();
        timingSceneDetailVO.setTimingId(familySceneTimingDO.getId());
        timingSceneDetailVO.setExecuteTime(DateUtils.toTimeString(familySceneTimingDO.getExecuteTime(), "HH:mm"));
        timingSceneDetailVO.setRepeatType(familySceneTimingDO.getType());
        timingSceneDetailVO.setSkipHoliday(familySceneTimingDO.getHolidaySkipFlag());

        // 重复设置
        if (Objects.equals(FamilySceneTimingRepeatTypeEnum.getByType(familySceneTimingDO.getType()), FamilySceneTimingRepeatTypeEnum.WEEK)) {
            String weekdayInChinese = FamilySceneTimingRepeatTypeEnum.WEEK.replaceWeek(familySceneTimingDO.getWeekday().split(EscapeCharacterConst.COMMA));
            timingSceneDetailVO.setRepeatValue(String.join(EscapeCharacterConst.SPACE, weekdayInChinese.split(EscapeCharacterConst.COMMA)));
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
        return returnSuccess(timingSceneDetailVO);
    }


}
