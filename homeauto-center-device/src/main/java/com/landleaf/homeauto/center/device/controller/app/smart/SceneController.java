package com.landleaf.homeauto.center.device.controller.app.smart;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.landleaf.homeauto.center.device.enums.SceneEnum;
import com.landleaf.homeauto.center.device.model.constant.FamilySceneTimingRepeatTypeEnum;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonSceneDO;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyTerminalDO;
import com.landleaf.homeauto.center.device.model.dto.FamilySceneCommonDTO;
import com.landleaf.homeauto.center.device.model.dto.SceneUpdateDTO;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilySceneBO;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilySceneTimingBO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyDeviceVO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneTimingVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneVO;
import com.landleaf.homeauto.center.device.model.vo.scene.family.PicVO;
import com.landleaf.homeauto.center.device.service.bridge.IAppService;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.center.device.util.DateUtils;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterSceneControlAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterSceneControlDTO;
import com.landleaf.homeauto.common.enums.device.TerminalTypeEnum;
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
    private IFamilyCommonSceneService familyCommonSceneService;

    @Autowired
    private IFamilyTerminalService familyTerminalService;

    @Autowired
    private IAppService appService;

    @Autowired
    private IDicTagService iDicTagService;

    @Autowired
    private IFamilySceneTimingService familySceneTimingService;

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
     * 执行场景
     *
     * @param familyId 家庭ID
     * @param sceneId  场景ID
     * @return Null
     */
    @PostMapping("/execute/{familyId}/{sceneId}")
    @ApiOperation("手动触发执行场景")
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

    @ApiOperation(value = "查询场景图片集合")
    @GetMapping("get/list/scene-pic")
    public Response<List<PicVO>> getListScenePic() {
        List<PicVO> result = iDicTagService.getListScenePic();
        return returnSuccess(result);
    }

    //---- 即将废弃的接口 ----

    /**
     * 查看全屋场景列表
     * <p>
     * 建议用 {@link SceneWholeHouseController#listWholeHouseScene(String)}
     */
    @Deprecated
    @GetMapping("/whole_house")
    @ApiOperation("查看全屋场景列表(旧)")
    public Response<List<FamilySceneVO>> listWholeHouseScene(@RequestParam String familyId) {
        List<FamilySceneDO> familySceneList = familySceneService.getFamilySceneByType(familyId, SceneEnum.WHOLE_HOUSE_SCENE);

        List<FamilySceneVO> familySceneVOList = new LinkedList<>();
        for (FamilySceneDO familySceneDO : familySceneList) {
            FamilySceneVO familySceneVO = new FamilySceneVO();
            familySceneVO.setSceneId(familySceneDO.getId());
            familySceneVO.setSceneName(familySceneDO.getName());
            familySceneVO.setSceneIcon(familySceneDO.getIcon());
            familySceneVO.setDefaultFlag(familySceneDO.getDefaultFlag());
            familySceneVOList.add(familySceneVO);
        }

        return returnSuccess(familySceneVOList);
    }

    /**
     * 查看全屋场景内容
     * <p>
     * 建议用 {@link SceneWholeHouseController#detailWhouseHouseScene(String)}
     *
     * @param sceneId 场景ID
     * @return 场景联动设备信息
     */
    @Deprecated
    @GetMapping("/detail")
    @ApiOperation("查看全屋场景内容(旧)")
    public Response<List<FamilyDeviceVO>> detailWhouseHouseScene(@RequestParam String sceneId) {
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
     * <p>
     * 建议用 {@link SceneWholeHouseController#edit(SceneUpdateDTO)}
     *
     * @param sceneUpdateDTO
     * @return
     */
    @Deprecated
    @PostMapping("/edit")
    @ApiOperation("编辑场景信息(旧)")
    public Response<?> editScene(@RequestBody SceneUpdateDTO sceneUpdateDTO) {
        UpdateWrapper<FamilySceneDO> familySceneUpdateWrapper = new UpdateWrapper<>();
        familySceneUpdateWrapper.set("icon", sceneUpdateDTO.getSceneIcon());
        familySceneUpdateWrapper.set("mode", sceneUpdateDTO.getSceneIconType());
        familySceneUpdateWrapper.eq("id", sceneUpdateDTO.getSceneId());
        familySceneService.update(familySceneUpdateWrapper);
        return returnSuccess();
    }

    /**
     * 查看定时场景列表
     * <p>
     * {@link SceneTimingController#getTimingSceneList(String)}
     *
     * @param familyId 家庭ID
     * @return
     */
    @Deprecated
    @GetMapping("/timing")
    @ApiOperation("查看定时场景列表(旧)")
    public Response<List<SceneTimingVO>> getTimingSceneList(@RequestParam String familyId) {
        List<FamilySceneTimingBO> familySceneTimingBOList = familySceneTimingService.listFamilySceneTiming(familyId);
        List<SceneTimingVO> sceneTimingVOList = new LinkedList<>();
        for (FamilySceneTimingBO familySceneTimingBO : familySceneTimingBOList) {
            SceneTimingVO sceneTimingVO = new SceneTimingVO();
            sceneTimingVO.setTimingId(familySceneTimingBO.getTimingSceneId());
            sceneTimingVO.setSceneName(familySceneTimingBO.getExecuteSceneName());
            sceneTimingVO.setExecuteTime(DateUtils.toTimeString(familySceneTimingBO.getExecuteTime(), "HH:mm"));
            sceneTimingVO.setEnabled(familySceneTimingBO.getEnabled());

            // 处理重复类型显示
            FamilySceneTimingRepeatTypeEnum sceneTimingRepeatTypeEnum = FamilySceneTimingRepeatTypeEnum.getByType(familySceneTimingBO.getRepeatType());
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
