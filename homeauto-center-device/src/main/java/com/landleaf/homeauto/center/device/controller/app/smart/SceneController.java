package com.landleaf.homeauto.center.device.controller.app.smart;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.landleaf.homeauto.center.device.enums.ProductPropertyEnum;
import com.landleaf.homeauto.center.device.enums.SceneEnum;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceWithPositionBO;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilySceneBO;
import com.landleaf.homeauto.center.device.model.bo.FamilySceneTimingBO;
import com.landleaf.homeauto.center.device.model.bo.SceneSimpleBO;
import com.landleaf.homeauto.center.device.model.constant.FamilySceneTimingRepeatTypeEnum;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonSceneDO;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneDO;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneTimingDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyTerminalDO;
import com.landleaf.homeauto.center.device.model.dto.FamilySceneCommonDTO;
import com.landleaf.homeauto.center.device.model.dto.SceneUpdateDTO;
import com.landleaf.homeauto.center.device.model.dto.TimingSceneDTO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyDeviceVO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneDetailVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneTimingDetailVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneTimingVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneVO;
import com.landleaf.homeauto.center.device.model.vo.scene.family.PicVO;
import com.landleaf.homeauto.center.device.service.bridge.IAppService;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.center.device.util.DateUtils;
import com.landleaf.homeauto.common.constant.EscapeCharacterConst;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterSceneControlAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterSceneControlDTO;
import com.landleaf.homeauto.common.enums.device.TerminalTypeEnum;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
import com.landleaf.homeauto.common.exception.ApiException;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Yujiumin
 * @version 2020/8/15
 */
@Slf4j
@RestController
@RequestMapping("/app/smart/scene")
@Api(value = "场景控制器", tags = "户式化APP场景接口")
public class SceneController extends BaseController {

    @Autowired
    private IHomeAutoFamilyService familyService;

    @Autowired
    private IFamilySceneService familySceneService;

    @Autowired
    private IFamilySceneTimingService familySceneTimingService;

    @Autowired
    private IFamilyDeviceService familyDeviceService;

    @Autowired
    private IFamilySceneActionService familySceneActionService;

    @Autowired
    private IFamilyCommonSceneService familyCommonSceneService;

    @Autowired
    private IFamilyTerminalService familyTerminalService;

    @Autowired
    private IAppService appService;

    @Autowired
    private IDicTagService iDicTagService;

    /**
     * 保存常用场景
     *
     * @param familySceneCommonDTO 常用场景传输对象
     * @return null
     */
    @PostMapping("/common/save")
    @ApiOperation("保存常用场景")
    @Transactional(rollbackFor = Exception.class)
    public Response<?> addFamilySceneCommon(@RequestBody FamilySceneCommonDTO familySceneCommonDTO) {
        familyCommonSceneService.saveCommonSceneList(familySceneCommonDTO.getFamilyId(), familySceneCommonDTO.getScenes());
        return returnSuccess();
    }

    /**
     * 获取不常用场景
     *
     * @param familyId 家庭ID
     * @return 不常用场景
     */
    @GetMapping("/uncommon")
    @ApiOperation(value = "获取不常用场景列表", notes = "从首页点击添加常用场景时, 获取待添加的常用场景列表")
    public Response<List<FamilySceneVO>> getFamilyUncommonScenesAndDevices(@RequestParam String familyId) {
        List<FamilySceneDO> familySceneDOList = familySceneService.listByFamilyId(familyId);
        List<FamilyCommonSceneDO> familyCommonSceneDOList = familyCommonSceneService.listByFamilyId(familyId);
        List<FamilySceneBO> familySceneBOList = familySceneService.getFamilySceneWithIndex(familySceneDOList, familyCommonSceneDOList, false);
        List<FamilySceneVO> familySceneVOList = new LinkedList<>();
        for (FamilySceneBO familySceneBO : familySceneBOList) {
            FamilySceneVO familySceneVO = new FamilySceneVO();
            familySceneVO.setSceneId(familySceneBO.getSceneId());
            familySceneVO.setSceneName(familySceneBO.getSceneName());
            familySceneVO.setSceneIcon(familySceneBO.getSceneIcon());
            familySceneVO.setIndex(familySceneBO.getSceneIndex());
            familySceneVOList.add(familySceneVO);
        }

        return returnSuccess(familySceneVOList);
    }

    /**
     * 查看全屋场景列表
     *
     * @param familyId 家庭ID
     * @return 全屋场景列表
     */
    @GetMapping("/whole-house/list")
    @ApiOperation("查看家庭全屋场景列表(建议用这个接口)")
    public Response<List<FamilySceneVO>> getWholeHouseScene(@RequestParam String familyId) {
        List<FamilySceneDO> familySceneList = familySceneService.getFamilySceneByType(familyId, SceneEnum.WHOLE_HOUSE_SCENE);

        List<FamilySceneVO> familySceneVOList = new LinkedList<>();
        for (FamilySceneDO familySceneDO : familySceneList) {
            FamilySceneVO familySceneVO = new FamilySceneVO();
            familySceneVO.setSceneId(familySceneDO.getId());
            familySceneVO.setSceneName(familySceneDO.getName());
            familySceneVO.setSceneIcon(familySceneDO.getIcon());
            familySceneVOList.add(familySceneVO);
        }

        return returnSuccess(familySceneVOList);
    }

    /**
     * 查看全屋场景内容
     *
     * @param sceneId 场景ID
     * @return 场景联动设备信息
     */
    @GetMapping("/whole-house/detail")
    @ApiOperation("查看场景内容")
    public Response<List<FamilyDeviceVO>> getWholeHouseDetail(@RequestParam String sceneId) {
        List<FamilyDeviceBO> linkageDeviceList = familySceneService.getLinkageDevice(sceneId);
        List<FamilyDeviceVO> familyDeviceVOList = new LinkedList<>();
        for (FamilyDeviceBO familyDeviceBO : linkageDeviceList) {
            FamilyDeviceVO familyDeviceVO = new FamilyDeviceVO();
            familyDeviceVO.setDeviceId(familyDeviceBO.getDeviceId());
            familyDeviceVO.setDeviceName(familyDeviceBO.getDeviceName());
            familyDeviceVO.setDeviceIcon(familyDeviceBO.getProductIcon());
            familyDeviceVO.setPosition(familyDeviceBO.getDevicePosition());
            familyDeviceVO.setDeviceAttrString(familyDeviceBO.getDeviceAttributeMap().values().stream().map(Objects::toString).collect(Collectors.joining("、")));
            familyDeviceVOList.add(familyDeviceVO);
        }
        return returnSuccess(familyDeviceVOList);
    }

    /**
     * 更新场景信息
     *
     * @param sceneUpdateDTO
     * @return
     */
    @PostMapping("/edit")
    @ApiOperation("编辑场景信息")
    public Response<Boolean> editScene(@RequestBody SceneUpdateDTO sceneUpdateDTO) {
        UpdateWrapper<FamilySceneDO> familySceneUpdateWrapper = new UpdateWrapper<>();
        familySceneUpdateWrapper.set("icon", sceneUpdateDTO.getSceneIcon());
        familySceneUpdateWrapper.set("mode", sceneUpdateDTO.getSceneIconType());
        familySceneUpdateWrapper.eq("id", sceneUpdateDTO.getSceneId());
        familySceneService.update(familySceneUpdateWrapper);
        return returnSuccess(true);
    }


    /**
     * 查看定时场景内容
     *
     * @param timingId 定时场景ID
     * @return {@link SceneTimingDetailVO}
     */
    @GetMapping("/timing/detail")
    @ApiOperation("查看定时场景内容")
    public Response<SceneTimingDetailVO> getTimingSceneDetail(@RequestParam String timingId) {
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


    /**
     * 添加定时场景
     *
     * @param timingSceneDTO 定时场景信息
     * @return 主键ID
     */
    @PostMapping("/timing/save")
    @ApiOperation("添加或编辑定时场景")
    public Response<String> addFamilySceneTiming(@RequestBody TimingSceneDTO timingSceneDTO) {
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
     * @param timingId 定时场景ID
     * @return Null
     */
    @PostMapping("timing/delete/{timingId}")
    @ApiOperation("删除定时场景")
    public Response<?> deleteFamilySceneTiming(@PathVariable String timingId) {
        FamilySceneTimingDO familySceneTimingDO = familySceneTimingService.getById(timingId);
        familySceneTimingService.removeById(timingId);

        // 通知大屏定时场景配置更新
        familySceneService.notifyConfigUpdate(familySceneTimingDO.getFamilyId(), ContactScreenConfigUpdateTypeEnum.SCENE_TIMING);
        return returnSuccess(familySceneTimingDO.getId());
    }

    /**
     * 执行场景
     *
     * @param familyId 家庭ID
     * @param sceneId  场景ID
     * @return Null
     */
    @PostMapping("/execute/{familyId}/{sceneId}")
    @ApiOperation("执行场景")
    public Response<?> execute(@PathVariable String familyId, @PathVariable String sceneId) {
        FamilySceneDO sceneDO = familySceneService.getById(sceneId);
        if (sceneDO == null) {
            throw new BusinessException(ErrorCodeEnumConst.CHECK_DATA_EXIST);
        }
        AdapterSceneControlDTO adapterSceneControlDTO = new AdapterSceneControlDTO();
        adapterSceneControlDTO.setFamilyId(familyId);
        adapterSceneControlDTO.setSceneId(sceneId);
        adapterSceneControlDTO.setFamilyCode(familyService.getById(familyId).getCode());
        adapterSceneControlDTO.setTime(System.currentTimeMillis());
        adapterSceneControlDTO.setSceneNo(StringUtils.isEmpty(sceneDO.getSceneNo()) ? sceneId : sceneDO.getSceneNo());

        // 终端设置
        FamilyTerminalDO familyTerminalDO = familyTerminalService.getMasterTerminal(familyId);
        adapterSceneControlDTO.setTerminalType(TerminalTypeEnum.getTerminal(familyTerminalDO.getType()).getCode());
        adapterSceneControlDTO.setTerminalMac(familyTerminalDO.getMac());
        AdapterSceneControlAckDTO adapterSceneControlAckDTO = appService.familySceneControl(adapterSceneControlDTO);
        if (Objects.isNull(adapterSceneControlAckDTO)) {
            throw new BusinessException("网络异常,请稍后再试!");
        } else if (!Objects.equals(adapterSceneControlAckDTO.getCode(), 200)) {
            throw new BusinessException(adapterSceneControlAckDTO.getMessage());
        }
        return returnSuccess();
    }

    @ApiOperation(value = "查询场景图片集合", notes = "")
    @GetMapping("get/list/scene-pic")
    public Response<List<PicVO>> getListScenePic() {
        List<PicVO> result = iDicTagService.getListScenePic();
        return returnSuccess(result);
    }

    //---- 即将废弃的接口 ----

    /**
     * 查看全屋场景列表
     *
     * @param familyId 家庭ID
     * @return {@link List<SceneVO>}
     */
    @Deprecated
    @GetMapping("/whole_house")
    @ApiOperation("查看家庭全屋场景列表(旧)")
    public Response<List<SceneVO>> getFamilyWholeHouseScenes(@RequestParam String familyId) {
        QueryWrapper<FamilySceneDO> familySceneQueryWrapper = new QueryWrapper<>();
        familySceneQueryWrapper.eq("type", SceneEnum.WHOLE_HOUSE_SCENE.getType());
        familySceneQueryWrapper.eq("family_id", familyId);
        List<FamilySceneDO> familySceneList = familySceneService.list(familySceneQueryWrapper);
        List<SceneVO> familySceneVOList = new LinkedList<>();
        for (FamilySceneDO familySceneDO : familySceneList) {
            SceneVO familySceneVO = new SceneVO();
            familySceneVO.setSceneId(familySceneDO.getId());
            familySceneVO.setSceneName(familySceneDO.getName());
            familySceneVO.setSceneIcon(familySceneDO.getIcon());
            familySceneVO.setIndex(0);
            familySceneVOList.add(familySceneVO);
        }
        return returnSuccess(familySceneVOList);
    }

    /**
     * 查看场景内容
     *
     * @param sceneId 场景ID
     * @return {@link List<SceneDetailVO>}
     */
    @Deprecated
    @GetMapping("/detail")
    @ApiOperation("查看场景内容")
    public Response<List<SceneDetailVO>> getSceneDetail(@RequestParam String sceneId) {
        List<FamilyDeviceWithPositionBO> familyDeviceWithPositionBOList = familyDeviceService.getDeviceInfoBySceneId(sceneId);
        List<SceneDetailVO> sceneDetailVOList = new LinkedList<>();
        for (FamilyDeviceWithPositionBO deviceWithPositionBO : familyDeviceWithPositionBOList) {
            SceneDetailVO sceneDetailVO = new SceneDetailVO();
            sceneDetailVO.setDeviceId(deviceWithPositionBO.getDeviceId());
            sceneDetailVO.setDeviceName(deviceWithPositionBO.getDeviceName());
            sceneDetailVO.setDeviceIcon(deviceWithPositionBO.getDeviceIcon());
            sceneDetailVO.setDevicePosition(String.format("%s-%s", deviceWithPositionBO.getFloorName(), deviceWithPositionBO.getRoomName()));

            // 设备属性
            Map<String, String> deviceAttrMap = familySceneActionService.getDeviceActionAttributionOnMapByDeviceSn(deviceWithPositionBO.getDeviceSn());
            sceneDetailVO.setDeviceAttrString(String.join("、", deviceAttrMap.values()));
            sceneDetailVO.setDeviceAttrs(deviceAttrMap);
            sceneDetailVOList.add(sceneDetailVO);
        }
        return returnSuccess(sceneDetailVOList);
    }

    /**
     * 查看定时场景列表
     *
     * @param familyId 家庭ID
     * @return {@link List<SceneTimingVO>}
     */
    @GetMapping("/timing")
    @ApiOperation("查看定时场景列表")
    public Response<List<SceneTimingVO>> getTimingSceneList(@RequestParam String familyId) {
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
        return returnSuccess(sceneTimingVOList);
    }


}
