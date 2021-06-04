package com.landleaf.homeauto.center.device.controller.app.smart;

import cn.jiguang.common.utils.StringUtils;
import com.landleaf.homeauto.center.device.enums.MaintenanceTypeEnum;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneTimingDO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.dto.DeviceCommandDTO;
import com.landleaf.homeauto.center.device.model.dto.FamilySceneCommonDTO;
import com.landleaf.homeauto.center.device.model.dto.TimingSceneDTO;
import com.landleaf.homeauto.center.device.model.dto.appversion.AppVersionDTO;
import com.landleaf.homeauto.center.device.model.dto.maintenance.FamilyMaintenanceAddRequestDTO;
import com.landleaf.homeauto.center.device.model.dto.msg.MsgNoticeAppDTO;
import com.landleaf.homeauto.center.device.model.dto.msg.MsgReadNoteDTO;
import com.landleaf.homeauto.center.device.model.smart.vo.*;
import com.landleaf.homeauto.center.device.model.vo.MyFamilyDetailInfoVO;
import com.landleaf.homeauto.center.device.model.vo.MyFamilyInfoVO;
import com.landleaf.homeauto.center.device.model.vo.device.error.AlarmMessageRecordVO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyUserOperateDTO;
import com.landleaf.homeauto.center.device.model.vo.family.app.FamiluseAddDTO;
import com.landleaf.homeauto.center.device.model.vo.family.app.FamiluserDeleteVO;
import com.landleaf.homeauto.center.device.model.vo.family.app.FamilyUpdateVO;
import com.landleaf.homeauto.center.device.model.vo.maintenance.FamilyMaintenanceRecordVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneTimingDetailVO;
import com.landleaf.homeauto.center.device.model.vo.scene.family.PicVO;
import com.landleaf.homeauto.center.device.service.AppService;
import com.landleaf.homeauto.center.device.service.IContactScreenService;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterDeviceStatusReadAckDTO;
import com.landleaf.homeauto.common.enums.FamilySystemFlagEnum;
import com.landleaf.homeauto.common.enums.oauth.AppTypeEnum;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.web.BaseController;
import com.landleaf.homeauto.common.web.context.TokenContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.landleaf.homeauto.common.web.context.TokenContextUtil.getUserIdForAppRequest;


/**
 * 尚缺少接口，查询状态接口、执行场景接口、执行设备控制接口
 */

/**
 * 户式化APP接口处理器
 * 2021/1/12 9:14
 *
 * @author wenyilu
 */
@RestController
@RequestMapping("/app/smart")
@Slf4j
@Api(value = "SmartAppController", tags = {"户式化APP接口处理器"})
public class SmartAppController extends BaseController {


    @Autowired
    private IHomeAutoFamilyService familyService;
    @Autowired
    private AppService appService;

    /*********************家庭相关********************************/
    /**
     * 获取用户家庭列表
     *
     * @param userId 用户ID
     * @return com.landleaf.homeauto.common.domain.Response<com.landleaf.homeauto.center.device.model.smart.vo.FamilySelectVO>
     * @author wenyilu
     * @date 2020/12/25 9:28
     */
    @GetMapping("/family/list")
    @ApiOperation(value = "家庭：获取用户家庭列表及上次切换家庭")
    public Response<FamilySelectVO> listFamily(@RequestParam(required = false) String userId) {
        if (StringUtils.isEmpty(userId)) {
            userId = TokenContext.getToken().getUserId();
        }
        return returnSuccess(appService.getUserFamily4VO(userId));
    }

    /**
     * 切换家庭
     *
     * @param familyId 家庭ID
     * @return com.landleaf.homeauto.common.domain.Response<com.landleaf.homeauto.center.device.model.smart.vo.FamilyCheckoutVO>
     * @author wenyilu
     * @date 2021/1/12 9:49
     */
    @GetMapping("/family/checkout/{familyId}")
    @ApiOperation(value = "家庭：切换家庭(包含常用场景及常用设备及天气信息)")
    public Response<FamilyCheckoutVO> switchFamily(@PathVariable("familyId") Long familyId) {
        return returnSuccess(appService.switchFamily(getUserIdForAppRequest(), familyId));
    }
    /*********************房间相关********************************/
    /**
     * 获取家庭下的所有楼层以及楼层下的房间信息
     *
     * @param familyId 家庭ID
     * @return 楼层下的房间信息
     */
    @GetMapping("/room/list/{familyId}")
    @ApiOperation("家庭：获取家庭楼层及房间列表")
    @ApiImplicitParam(name = "deviceFilterFlag", value = "0:无需包含设备，1：需包含暖通，2：需包含子设备或普通设备，3：三种类型设备都要包含", paramType = "query", required = false)
    public Response<List<FamilyFloorVO>> listFloorAndRoom(@PathVariable("familyId") Long familyId,
                                                          @RequestParam(required = false,value = "deviceFilterFlag")Integer deviceFilterFlag) {
        return returnSuccess(appService.getFamilyFloor4VO(familyId,deviceFilterFlag));
    }

    /**
     * 通过roomId获取设备列表
     */
    @GetMapping("/device/list/{familyId}/{roomId}")
    @ApiOperation(value = "房间：获取家庭房间设备列表-非系统设备")
    public Response<List<FamilyDeviceSimpleVO>> getRoomDevices(@PathVariable("familyId") Long familyId,
                                                         @PathVariable("roomId") Long roomId) {
        return returnSuccess(appService.getFamilyDevices4VO(familyId,
                roomId, FamilySystemFlagEnum.NORMAL_DEVICE.getType()));
    }
    /**
     * 通过roomId获取系统下子设备列表
     */
    @GetMapping("/device/sys-sub/list/{familyId}/{roomId}")
    @ApiOperation(value = "房间：获取家庭房间设备列表-系统下子设备")
    public Response<List<FamilyDeviceSimpleVO>> getRoomPanelDevices(@PathVariable("familyId") Long familyId,
                                                         @PathVariable("roomId") Long roomId) {
        return returnSuccess(appService.getFamilyDevices4VO(familyId,
                roomId,FamilySystemFlagEnum.SYS_SUB_DEVICE.getType()));
    }
    /**
     * 获取家庭下暖通设备（系统设备）
     */
    @GetMapping("/device/system/list/{familyId}")
    @ApiOperation(value = "房间：获取家庭-系统设备")
    public Response<List<FamilyDeviceSimpleVO>> getRoomPanelDevices(@PathVariable("familyId") Long familyId) {
        return returnSuccess(appService.getFamilyDevices4VO(familyId,
                null,FamilySystemFlagEnum.SYS_DEVICE.getType()));
    }

    /*********************家庭管理相关********************************/
    @GetMapping("/family-manager/my/list")
    @ApiOperation(value = "家庭管理：获取我的家庭家庭列表(包含房间数、设备数、成员数)")
    public Response<List<MyFamilyInfoVO>> getMyFamily() {
        return returnSuccess(appService.getMyFamily4VO(getUserIdForAppRequest()));
    }

    @GetMapping("/family-manager/my/info/{familyId}")
    @ApiOperation("家庭管理：获取某个家庭详情：楼层、房间、设备、用户信息等简要信息")
    public Response<MyFamilyDetailInfoVO> getMyFamilyInfo(@PathVariable("familyId") String familyId) {
        return returnSuccess(appService.getMyFamilyInfo4VO(BeanUtil.convertString2Long(familyId)));
    }

    @PostMapping("/family-manager/delete/member")
    @ApiOperation("家庭管理：移除家庭成员")
    public Response deleteFamilyMember(@RequestBody FamiluserDeleteVO familuserDeleteVO) {
        appService.deleteFamilyMember(familuserDeleteVO);
        return returnSuccess();
    }

    @PostMapping("/family-manager/quit/family/{familyId}")
    @ApiOperation("家庭管理：退出家庭")
    public Response quitFamily(@PathVariable("familyId") String familyId) {
        appService.quitFamily(BeanUtil.convertString2Long(familyId), getUserIdForAppRequest());
        return returnSuccess();
    }

    @PostMapping("/family-manager/add/{familyId}")
    @ApiOperation("家庭管理：扫码绑定家庭")
    public Response addFamilyMember(@PathVariable("familyId") String familyId) {
        //扫码获取的格式 type:家庭id/家庭编号
        appService.addFamilyMember(familyId, getUserIdForAppRequest());
        return returnSuccess();  // type:familyId 1:12344
    }

    @PostMapping("/family-manager/add")
    @ApiOperation("家庭管理：绑定家庭")
    public Response addFamilyMember(@RequestBody FamiluseAddDTO request) {
        appService.addFamilyMember(request, getUserIdForAppRequest());
        return returnSuccess();
    }

    @PostMapping("/family-manager/update/family")
    @ApiOperation("家庭：修改家庭名称")
    public Response updateFamilyName(@RequestBody FamilyUpdateVO request) {
        appService.updateFamilyName(request);
        return returnSuccess();
    }

    @ApiOperation(value = "家庭管理：设置为管理员", notes = "", consumes = "application/json")
    @PostMapping("/family-manager/setting/admin")
    public Response settingAdmin(@RequestBody FamilyUserOperateDTO request) {
        appService.settingAdmin(request);
        return returnSuccess();
    }

    /*********************设备相关********************************/


//    /**
//     * 查询不同模式下的温度范围
//     */
//    @GetMapping("/device/attr/scope/{familyId}")
//    @ApiOperation(value = "设备: 查询家庭中制冷制热模式下的值域范围")
//    public Response<List<FamilyModeScopeVO>> getFamilyModeScopeConfig(@PathVariable("familyId") String familyId) {
//        return returnSuccess(familyService.getFamilyModeScopeConfig(BeanUtil.convertString2Long(familyId)));
//    }

    /**
     * 查询设备当前运行状态
     *
     * @param deviceId 设备ID
     * @return 设备状态信息
     */
    @GetMapping("/device/status/{familyId}/{deviceId}")
    @ApiOperation(value = "设备: 查看设备状态(系统子设备+普通设备)")
    public Response<Map<String, Object>> getDeviceStatus(@PathVariable String familyId, @PathVariable String deviceId) {
        Map<String, Object> deviceStatus4VO = appService.getDeviceStatus4VO(BeanUtil.convertString2Long(familyId),
                BeanUtil.convertString2Long(deviceId));
        return returnSuccess(deviceStatus4VO);
    }
    /**
     * 查询系统当前运行状态
     *
     * @return 设备状态信息
     */
    @GetMapping("/device/system/status/{familyId}")
    @ApiOperation(value = "设备: 查询系统当前运行状态")
    public Response<Map<String, Object>> getSystemStatus(@PathVariable String familyId) {

        Map<String, Object> systemStatus4VO = appService.getSystemStatusVO(BeanUtil.convertString2Long(familyId));

        return returnSuccess(systemStatus4VO);
    }

    /**
     * 户式化 APP 设备控制接口
     *
     * @param deviceCommandDTO 指令
     * @return 执行结果
     */
    @PostMapping("/device/execute")
    @ApiOperation(value = "设备: 设备控制")
    public Response<?> command(@RequestBody DeviceCommandDTO deviceCommandDTO) {
        Long familyId = deviceCommandDTO.getFamilyId();
        HomeAutoFamilyDO homeAutoFamilyDO = familyService.getById(familyId);
        if (homeAutoFamilyDO.getEnableStatus().intValue() == 0) {
            throw new BusinessException(ErrorCodeEnumConst.FAMILY_DISABLE);
        }
        appService.sendCommand(deviceCommandDTO);
        return returnSuccess();
    }

    @ApiOperation(value = "读取设备状态", notes = "读取设备状态", consumes = "application/json")
    @PostMapping(value = "/read/status/{familyId}/{deviceId}")
    public Response<AdapterDeviceStatusReadAckDTO> readStatus(@PathVariable("familyId") String familyId,
                                                              @PathVariable("deviceId") String deviceId) {
        return returnSuccess(appService.readDeviceStatus(BeanUtil.convertString2Long(familyId),
                BeanUtil.convertString2Long(deviceId)));
    }

    /*********************消息相关********************************/

    @GetMapping("/msg/list/{familyId}")
    @ApiOperation("消息: 获取消息公告列表")
    public Response<List<MsgNoticeAppDTO>> getMsgList4VO(@PathVariable("familyId") String familyId) {
        return returnSuccess(appService.getMsgList4VO(BeanUtil.convertString2Long(familyId)));
    }

    @PostMapping("/msg/add-read/note")
    @ApiOperation("消息: 添加消息已读记录")
    public Response addReadNote(@RequestBody MsgReadNoteDTO request) {
        appService.addReadNote(request);
        return returnSuccess();
    }

    /*********************场景相关********************************/
    /**
     * 保存常用场景
     *
     * @param familySceneCommonDTO 常用场景传输对象
     * @return null
     */
    @PostMapping("/scene/common/save")
    @ApiOperation("场景: 保存常用场景")
    @Transactional(rollbackFor = Exception.class)
    public Response<?> addFamilySceneCommon(@RequestBody FamilySceneCommonDTO familySceneCommonDTO) {
        List<String> scenes = familySceneCommonDTO.getScenes();
        if (!CollectionUtils.isEmpty(scenes)) {
            appService.saveCommonSceneList(BeanUtil.convertString2Long(familySceneCommonDTO.getFamilyId()),
                    scenes.stream().map(i -> {
                        return BeanUtil.convertString2Long(i);
                    }).collect(Collectors.toList()));
        }
        return returnSuccess();
    }

    /**
     * 获取家庭不常用场景
     *
     * @param familyId 家庭ID
     * @return 不常用场景
     */
    @GetMapping("/scene/uncommon")
    @ApiOperation(value = "场景: 获取不常用场景列表")
    public Response<List<FamilySceneVO>> getFamilyUncommonScenes4VOByFamilyId(@RequestParam String familyId) {
        return returnSuccess(appService.getFamilyUncommonScenes4VOByFamilyId(BeanUtil.convertString2Long(familyId)));
    }

    /**
     * 执行场景
     *
     * @param familyId 家庭ID
     * @param sceneId  场景ID
     * @return Null
     */
    @PostMapping("/scene/execute/{familyId}/{sceneId}")
    @ApiOperation("场景: 手动触发执行场景")
    public Response<?> execute(@PathVariable String familyId, @PathVariable String sceneId) {
        appService.executeScene(BeanUtil.convertString2Long(sceneId), BeanUtil.convertString2Long(familyId));
        return returnSuccess();
    }

    @ApiOperation(value = "场景:查询场景图片集合")
    @GetMapping("/scene/get/list/scene-pic")
    public Response<List<PicVO>> getListScenePic() {
        List<PicVO> result = appService.getListScenePic();
        return returnSuccess(result);
    }


    /*********************场景定时相关********************************/
    /**
     * 查看定时场景列表
     *
     * @param familyId 家庭ID
     * @return
     */
    @GetMapping("/scene/timing/list")
    @ApiOperation("场景定时: 查看定时场景列表")
    public Response<List<FamilySceneTimingVO>> getTimingSceneList(@RequestParam("familyId") String familyId) {
        return returnSuccess(appService.getTimingSceneList(BeanUtil.convertString2Long(familyId)));
    }

    /**
     * 查看定时场景内容
     *
     * @param timingId 定时场景ID
     * @return 定时场景内容
     */
    @GetMapping("/scene/timing/detail")
    @ApiOperation("场景定时: 查看定时场景内容")
    public Response<SceneTimingDetailVO> getTimingSceneDetail(@RequestParam String timingId) {
        return returnSuccess(appService.getTimingSceneDetail(BeanUtil.convertString2Long(timingId)));
    }

    /**
     * 添加或修改定时场景
     *
     * @param timingSceneDTO 定时场景信息
     * @return 操作结果
     */
    @PostMapping("/scene/timing/save")
    @ApiOperation("场景定时: 添加定时场景")
    public Response<Boolean> save(@RequestBody TimingSceneDTO timingSceneDTO) {
        appService.saveTimingScene(timingSceneDTO);
        // 通知大屏定时配置更新
        try {
            appService.notifySceneTimingConfigUpdate(BeanUtil.convertString2Long(timingSceneDTO.getFamilyId()),
                    ContactScreenConfigUpdateTypeEnum.SCENE_TIMING);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnSuccess();
    }

    /**
     * 删除定时场景
     *
     * @param timingSceneId 定时场景ID
     * @return 操作结果
     */
    @PostMapping("/scene/timing/delete/{timingSceneId}")
    @ApiOperation("场景定时: 删除定时场景")
    public Response<?> deleteFamilySceneTiming(@PathVariable Long timingSceneId) {
        appService.deleteFamilySceneTiming(timingSceneId);
        return returnSuccess();
    }

    /**
     * 启用(禁用)定时场景
     *
     * @param sceneTimingId 定时场景ID
     * @return 操作结果
     */
    @PostMapping("/scene/timing/switch/toggle/{sceneTimingId}")
    @ApiOperation("场景定时: 定时场景启用(禁用)接口")
    public Response<Boolean> enableSceneTiming(@PathVariable Long sceneTimingId) {
        appService.enableSceneTiming(sceneTimingId);

        return returnSuccess(true);
    }
    /*********************全屋场景情景相关********************************/
    /**
     * 查看场景列表
     *
     * @param familyId 家庭ID
     * @return 全屋场景列表
     */
    @GetMapping("/scene/whole-house/list")
    @ApiOperation("场景：查看家庭场景列表")
    public Response<List<FamilySceneVO>> listWholeHouseScene(@RequestParam String familyId) {
        return returnSuccess(appService.listWholeHouseScene(BeanUtil.convertString2Long(familyId)));
    }


    /*********************安防报警相关********************************/
    @GetMapping("/alarm-message/list/{familyId}/{deviceId}")
    @ApiOperation("安防报警: 获取报警记录列表")
    public Response<List<AlarmMessageRecordVO>> getAlarmlist(@PathVariable("familyId") String familyId) {
        List<AlarmMessageRecordVO> msglist = appService.getAlarmlistByDeviceId(null, familyId);
        return returnSuccess(msglist);
    }

    /*********************版本相关********************************/
    @ApiOperation("版本: 根据app类型获取当前已推送的最新版本")
    @GetMapping("/app-version/current/{appType}")
    public Response<AppVersionDTO> currentVersion(@PathVariable("appType") Integer appType) {
        AppVersionDTO version = appService.getCurrentVersion(appType, AppTypeEnum.SMART.getCode());
        return returnSuccess(version);
    }

    /*****************************维保记录*****************************/
    @ApiOperation(value = "维保记录: 创建", notes = "创建报修", consumes = "application/json")
    @PostMapping(value = "/maintenance/add")
    public Response addMaintenanceRecord(@RequestBody FamilyMaintenanceAddRequestDTO requestBody) {
        requestBody.setMaintenanceType(MaintenanceTypeEnum.report.getCode());
        appService.addMaintenanceRecord(requestBody);
        return returnSuccess();
    }

    @ApiOperation(value = "维保记录: 列表查询")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header", required = true)
    @PostMapping(value = "/maintenance/list")
    public Response<List<FamilyMaintenanceRecordVO>> listMaintenanceRecords(@RequestParam Long familyId) {
        return returnSuccess(appService.listMaintenanceRecords(familyId));
    }

    @ApiOperation(value = "维保记录: 详情查询")
    @GetMapping(value = "/maintenance/detail")
    public Response<FamilyMaintenanceRecordVO> getMaintenanceDetail(@RequestParam("id") Long id) {
        return returnSuccess(appService.getMaintenanceDetail(id));
    }


    /*********************故障报修相关********************************/
//    @ApiOperation("故障报修: 获取故障内容可选值下拉框")
//    @GetMapping("/fault-report/repair-apperance")
//    public Response<List<KvObject>> getRepairApperance() {
//        List<KvObject> options = Lists.newArrayList();
//        List<SobotTicketTypeFiledOption> tmpResult = sobotService.getRepirApperanceOptions();
//        if (!CollectionUtils.isEmpty(tmpResult)) {
//            options.addAll(tmpResult.stream().map(i -> {
//                KvObject data = new KvObject();
//                data.setKey(i.getDataName());
//                data.setValue(i.getDataValue());
//                return data;
//            }).collect(Collectors.toList()));
//        }
//        return returnSuccess(options);
//    }
//
//    @ApiOperation("故障报修: 根据家庭获取暖通设备名称下拉框(安卓)")
//    @GetMapping("/fault-report/device-name")
//    public Response<Set<KvObject>> getFamilyDeviceName(@RequestParam("familyId") String familyId) {
//        Set<KvObject> options = Sets.newHashSet();
//        KvObject kvObject = new KvObject();
//        kvObject.setValue("手动输入");
//        kvObject.setKey("手动输入");
//        options.add(kvObject);
//        return returnSuccess(options);
//    }
//
//    @ApiOperation("故障报修: 根据家庭获取暖通设备名称下拉框(IOS)")
//    @GetMapping("/fault-report/device-name/{familyId}")
//    public Response<Set<KvObject>> getFamilyDeviceName2(@PathVariable("familyId") String familyId) {
//        return getFamilyDeviceName(familyId);
//    }
//
//    @ApiOperation(value = "故障报修: 故障报修详情查询(安卓)")
//    @GetMapping(value = "/fault-report/detail")
//    public Response<AppRepairDetailDTO> getRepairDetail(@RequestParam("repairId") String repairId) {
//        AppRepairDetailDTO data = homeAutoFaultReportService.getRepairDetail(repairId);
//        return returnSuccess(data);
//    }
//
//    @ApiOperation(value = "故障报修: 故障报修详情查询(IOS)")
//    @GetMapping(value = "/fault-report/detail/{repairId}")
//    public Response<AppRepairDetailDTO> getRepairDetail2(@PathVariable("repairId") String repairId) {
//        return getRepairDetail(repairId);
//    }
//
//    @ApiOperation(value = "故障报修: 故障报修记录查询(安卓)")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header", required = true)
//    @PostMapping(value = "/fault-report/list")
//    public Response<List<AppRepairDetailDTO>> listRepairs(@RequestParam String familyId) {
//        return returnSuccess(homeAutoFaultReportService.listRepairs(familyId));
//    }
//
//    @ApiOperation(value = "故障报修: 故障报修记录查询(IOS)")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header", required = true)
//    @PostMapping(value = "/fault-report/list/{familyId}")
//    public Response<List<AppRepairDetailDTO>> listRepairs2(@PathVariable String familyId) {
//        return listRepairs(familyId);
//    }
//
//    @ApiOperation(value = "故障报修: 创建报修", notes = "创建报修", consumes = "application/json")
//    @PostMapping(value = "/fault-report/add")
//    public Response createRepair(@RequestBody RepairAddReqDTO requestBody) {
//
//        homeAutoFaultReportService.createRepair(requestBody, TokenContext.getToken().getUserId());
//        return returnSuccess();
//    }
//
//    @ApiOperation(value = "故障报修: 修改状态为已完成(安卓)", notes = "修改状态为已完成", consumes = "application/json")
//    @PostMapping(value = "/fault-report/status/completed")
//    public Response completed(@RequestParam("repairId") String repairId) {
//
//        homeAutoFaultReportService.completed(repairId, TokenContext.getToken().getUserId());
//        return returnSuccess();
//    }
//
//    @ApiOperation(value = "故障报修: 修改状态为已完成(IOS)", notes = "修改状态为已完成", consumes = "application/json")
//    @PostMapping(value = "/fault-report/status/completed/{repairId}")
//    public Response completed2(@PathVariable("repairId") String repairId) {
//
//        return completed(repairId);
//    }


}
