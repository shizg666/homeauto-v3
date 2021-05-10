package com.landleaf.homeauto.center.device.controller.app.smart;

import cn.jiguang.common.utils.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.landleaf.homeauto.center.device.config.ImagePathConfig;
import com.landleaf.homeauto.center.device.enums.RoomTypeEnum;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneTimingDO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.dto.DeviceCommandDTO;
import com.landleaf.homeauto.center.device.model.dto.FamilyDeviceCommonDTO;
import com.landleaf.homeauto.center.device.model.dto.FamilySceneCommonDTO;
import com.landleaf.homeauto.center.device.model.dto.TimingSceneDTO;
import com.landleaf.homeauto.center.device.model.dto.appversion.AppVersionDTO;
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
import com.landleaf.homeauto.center.device.model.vo.scene.SceneTimingDetailVO;
import com.landleaf.homeauto.center.device.model.vo.scene.family.PicVO;
import com.landleaf.homeauto.center.device.service.IContactScreenService;
import com.landleaf.homeauto.center.device.service.SobotService;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.KvObject;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterDeviceStatusReadAckDTO;
import com.landleaf.homeauto.common.domain.dto.device.repair.AppRepairDetailDTO;
import com.landleaf.homeauto.common.domain.dto.device.repair.RepairAddReqDTO;
import com.landleaf.homeauto.common.domain.po.device.sobot.SobotTicketTypeFiledOption;
import com.landleaf.homeauto.common.enums.oauth.AppTypeEnum;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.web.BaseController;
import com.landleaf.homeauto.common.web.context.TokenContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.landleaf.homeauto.common.web.context.TokenContextUtil.getUserIdForAppRequest;


/**
 * TODO
 * 尚缺少接口，查询状态接口、执行场景接口、执行设备控制接口
 */

/**
 *  户式化APP接口处理器
 *  2021/1/12 9:14
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
    private IFamilyCommonSceneService familyCommonSceneService;
    @Autowired
    private IFamilyCommonDeviceService familyCommonDeviceService;
    @Autowired
    private IHomeAutoAlarmMessageService homeAutoAlarmMessageService;
    @Autowired
    private IHomeAutoAppVersionService homeAutoAppVersionService;
    @Autowired
    private IFamilyUserService familyUserService;
    @Autowired
    private IHomeautoFaultReportService homeAutoFaultReportService;
    @Autowired
    private SobotService sobotService;
    @Autowired
    private IMsgNoticeService msgNoticeService;
    @Autowired
    private IMsgReadNoteService msgReadNoteService;
    @Autowired
    private ImagePathConfig imagePathConfig;
    @Autowired
    private IDicTagService dicTagService;
    @Autowired
    private IFamilySceneTimingService familySceneTimingService;
    @Autowired
    private IContactScreenService contactScreenService;


    /*********************家庭相关********************************/
    /**
     * 获取用户家庭列表
     *
     * @param userId  用户ID
     * @return com.landleaf.homeauto.common.domain.Response<com.landleaf.homeauto.center.device.model.smart.vo.FamilySelectVO>
     * @author wenyilu
     * @date 2020/12/25 9:28
     */
    @GetMapping("/family/list")
    @ApiOperation(value = "家庭：获取用户家庭列表及上次切换家庭")
    public Response<FamilySelectVO> listFamily(@RequestParam(required = false) String userId) {
        if(StringUtils.isEmpty(userId)){
            userId=TokenContext.getToken().getUserId();
        }
        return returnSuccess(familyService.getUserFamily4VO(userId));
    }

    /**
     *  切换家庭
     * @param familyId  家庭ID
     * @return com.landleaf.homeauto.common.domain.Response<com.landleaf.homeauto.center.device.model.smart.vo.FamilyCheckoutVO>
     * @author wenyilu
     * @date 2021/1/12 9:49
     */
    @GetMapping("/family/checkout/{familyId}")
    @ApiOperation(value = "家庭：切换家庭(包含常用场景及常用设备及天气信息)")
    public Response<FamilyCheckoutVO> switchFamily(@PathVariable("familyId") String familyId) {
        return returnSuccess(familyService.switchFamily(getUserIdForAppRequest(), BeanUtil.convertString2Long(familyId)));
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
    public Response<List<FamilyFloorVO>> listFloorAndRoom(@PathVariable("familyId") String familyId) {
        return returnSuccess(familyService.getFamilyFloor4VO(BeanUtil.convertString2Long(familyId)));
    }

    /**
     * 获取房间图片
     */
    @GetMapping("/room/pic/list")
    @ApiOperation("房间：获取房间图片")
    public Response<List<String>> getRoomPic() {
        return returnSuccess(Arrays.stream(RoomTypeEnum.values()).map(room -> imagePathConfig.getContext()
                .concat(room.getIcon())).collect(Collectors.toList()));
    }

    /**
     * 通过roomId获取设备列表
     */
    @GetMapping("/device/list/{familyId}/{roomId}")
    @ApiOperation(value = "房间：获取家庭房间设备列表")
    public Response<List<FamilyDeviceVO>> getRoomDevices(@PathVariable("familyId") String familyId,
                                                         @PathVariable("roomId")String roomId) {
        return returnSuccess(familyService.getFamilyDevices4VO(BeanUtil.convertString2Long(familyId),
                BeanUtil.convertString2Long( roomId)));
    }

    /*********************家庭管理相关********************************/
    @GetMapping("/family-manager/my/list")
    @ApiOperation(value = "家庭管理：获取我的家庭家庭列表(包含房间数、设备数、成员数)")
    public Response<List<MyFamilyInfoVO>> getMyFamily() {
        return returnSuccess(familyService.getMyFamily4VO(getUserIdForAppRequest()));
    }

    @GetMapping("/family-manager/my/info/{familyId}")
    @ApiOperation("家庭管理：获取某个家庭详情：楼层、房间、设备、用户信息等简要信息")
    public Response<MyFamilyDetailInfoVO> getMyFamilyInfo(@PathVariable("familyId") String familyId) {
        return returnSuccess(familyService.getMyFamilyInfo4VO(BeanUtil.convertString2Long(familyId)));
    }

    @PostMapping("/family-manager/delete/member")
    @ApiOperation("家庭管理：移除家庭成员")
    public Response deleteFamilyMember(@RequestBody FamiluserDeleteVO familuserDeleteVO) {
        familyUserService.deleteFamilyMember(familuserDeleteVO);
        return returnSuccess();
    }

    @PostMapping("/family-manager/quit/family/{familyId}")
    @ApiOperation("家庭管理：退出家庭")
    public Response quitFamily(@PathVariable("familyId") String familyId) {
        familyUserService.quitFamily(BeanUtil.convertString2Long(familyId), getUserIdForAppRequest());
        return returnSuccess();
    }

    @PostMapping("/family-manager/add/{familyId}")
    @ApiOperation("家庭管理：扫码绑定家庭")
    public Response addFamilyMember(@PathVariable("familyId") String familyId) {
        //扫码获取的格式 type:家庭id/家庭编号
        familyUserService.addFamilyMember(familyId, getUserIdForAppRequest());
        return returnSuccess();  // type:familyId 1:12344
    }

    @PostMapping("/family-manager/add")
    @ApiOperation("家庭管理：绑定家庭")
    public Response addFamilyMember(@RequestBody FamiluseAddDTO request) {
        familyUserService.addFamilyMember(request, getUserIdForAppRequest());
        return returnSuccess();
    }

    @PostMapping("/family-manager/update/family")
    @ApiOperation("家庭：修改家庭名称")
    public Response updateFamilyName(@RequestBody FamilyUpdateVO request) {
        familyService.updateFamilyName(request);
        return returnSuccess();
    }

    @ApiOperation(value = "家庭管理：设置为管理员", notes = "", consumes = "application/json")
    @PostMapping("/family-manager/setting/admin")
    public Response settingAdmin(@RequestBody FamilyUserOperateDTO request) {
        familyUserService.settingAdmin(request);
        return returnSuccess();
    }

    /*********************设备相关********************************/
    /**
     * 保存常用设备
     *
     * @param familyDeviceCommonDTO 常用设备列表
     * @return 操作结果
     */
    @PostMapping("/device/common/save")
    @ApiOperation(value = "设备: 保存常用设备")
    public Response<Void> addFamilyDeviceCommon(@RequestBody FamilyDeviceCommonDTO familyDeviceCommonDTO) {
        List<String> devices = familyDeviceCommonDTO.getDevices();
        if(!CollectionUtils.isEmpty(devices)){
            familyCommonDeviceService.saveCommonDeviceList(BeanUtil.convertString2Long(familyDeviceCommonDTO.getFamilyId()), devices.stream().map(i->{return BeanUtil.convertString2Long(i);}).collect(Collectors.toList()));
        }
        return returnSuccess();
    }

    /**
     * 获取家庭不常用的设备
     *
     * @param familyId 家庭ID
     * @return 不常用设备列表
     */
    @GetMapping("/device/uncommon")
    @ApiOperation(value = "设备: 获取不常用的设备")
    public Response<List<FamilyUncommonDeviceVO>> getUncommonDevices(@RequestParam("familyId") String familyId) {
        return returnSuccess(familyCommonDeviceService.getUnCommonDevices4VO(BeanUtil.convertString2Long(familyId)));
    }

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
    @ApiOperation(value = "设备: 查看设备状态")
    public Response<Map<String, Object>> getDeviceStatus(@PathVariable String familyId, @PathVariable String deviceId) {
        Map<String, Object> deviceStatus4VO = familyService.getDeviceStatus4VO(BeanUtil.convertString2Long(familyId),
                BeanUtil.convertString2Long(deviceId));
        return returnSuccess(deviceStatus4VO);
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
        String familyId = deviceCommandDTO.getFamilyId();
        HomeAutoFamilyDO homeAutoFamilyDO = familyService.getById(BeanUtil.convertString2Long(familyId));
        if(homeAutoFamilyDO.getEnableStatus().intValue()==0){
            throw new BusinessException(ErrorCodeEnumConst.FAMILY_DISABLE);
        }
        familyService.sendCommand(deviceCommandDTO);
        return returnSuccess();
    }

    @ApiOperation(value = "读取设备状态", notes = "读取设备状态", consumes = "application/json")
    @PostMapping(value = "/read/status/{familyId}/{deviceId}")
    public Response<AdapterDeviceStatusReadAckDTO> readStatus(@PathVariable("familyId") String familyId,
                                                              @PathVariable("deviceId")String deviceId) {
        return returnSuccess(familyService.readDeviceStatus(BeanUtil.convertString2Long(familyId),
                BeanUtil.convertString2Long(deviceId)));
    }
    /*********************消息相关********************************/

    @GetMapping("/msg/list/{familyId}")
    @ApiOperation("消息: 获取消息公告列表")
    public Response<List<MsgNoticeAppDTO>> getMsgList4VO(@PathVariable("familyId") String familyId) {
        return returnSuccess(msgNoticeService.getMsgList4VO(BeanUtil.convertString2Long(familyId)));
    }

    @PostMapping("/msg/add-read/note")
    @ApiOperation("消息: 添加消息已读记录")
    public Response addReadNote(@RequestBody MsgReadNoteDTO request) {
        msgReadNoteService.addReadNote(request);
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
        if(!CollectionUtils.isEmpty(scenes)){
            familyCommonSceneService.saveCommonSceneList(BeanUtil.convertString2Long(familySceneCommonDTO.getFamilyId()),
                    scenes.stream().map(i->{return BeanUtil.convertString2Long(i);}).collect(Collectors.toList()) );
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
        return returnSuccess(familyCommonSceneService.getFamilyUncommonScenes4VOByFamilyId(BeanUtil.convertString2Long(familyId)));
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
        familyService.executeScene(BeanUtil.convertString2Long(sceneId), BeanUtil.convertString2Long(familyId));
        return returnSuccess();
    }

    @ApiOperation(value = "场景:查询场景图片集合")
    @GetMapping("/scene/get/list/scene-pic")
    public Response<List<PicVO>> getListScenePic() {
        List<PicVO> result = dicTagService.getListScenePic();
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
        return returnSuccess(familySceneTimingService.getTimingSceneList(BeanUtil.convertString2Long(familyId)));
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
        return returnSuccess(familySceneTimingService.getTimingSceneDetail(BeanUtil.convertString2Long(timingId)));
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
        familySceneTimingService.saveTimingScene(timingSceneDTO);
        // 通知大屏定时配置更新
        try {
            contactScreenService.notifySceneTimingConfigUpdate(BeanUtil.convertString2Long(timingSceneDTO.getFamilyId()),
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
    public Response<?> deleteFamilySceneTiming(@PathVariable String timingSceneId) {
        FamilySceneTimingDO familySceneTimingDO = familySceneTimingService.getById(BeanUtil.convertString2Long(timingSceneId));
        familySceneTimingService.removeById(BeanUtil.convertString2Long(timingSceneId));

        // 通知大屏定时场景配置更新
        try {
            contactScreenService.notifySceneTimingConfigUpdate(familySceneTimingDO.getFamilyId(), ContactScreenConfigUpdateTypeEnum.SCENE_TIMING);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public Response<Boolean> enableSceneTiming(@PathVariable String sceneTimingId) {
        FamilySceneTimingDO familySceneTimingDO = familySceneTimingService.getById(BeanUtil.convertString2Long(sceneTimingId));
        int targetEnabled = (familySceneTimingDO.getEnableFlag() + 1) % 2;
        familySceneTimingService.updateEnabled(BeanUtil.convertString2Long(sceneTimingId), targetEnabled);
        // 通知大屏定时场景配置更新
        try {
            contactScreenService.notifySceneTimingConfigUpdate(familySceneTimingDO.getFamilyId(), ContactScreenConfigUpdateTypeEnum.SCENE_TIMING);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        return returnSuccess(familyService.listWholeHouseScene(BeanUtil.convertString2Long(familyId)));
    }


    /*********************安防报警相关********************************/
    @GetMapping("/alarm-message/list/{familyId}/{deviceId}")
    @ApiOperation("安防报警: 获取报警记录列表")
    public Response<List<AlarmMessageRecordVO>> getAlarmlist(@PathVariable("familyId") String familyId) {
        List<AlarmMessageRecordVO> msglist = homeAutoAlarmMessageService.getAlarmlistByDeviceId(null, familyId);
        return returnSuccess(msglist);
    }

    /*********************版本相关********************************/
    @ApiOperation("版本: 根据app类型获取当前已推送的最新版本")
    @GetMapping("/app-version/current/{appType}")
    public Response<AppVersionDTO> currentVersion(@PathVariable("appType") Integer appType) {
        AppVersionDTO version = homeAutoAppVersionService.getCurrentVersion(appType, AppTypeEnum.SMART.getCode());
        return returnSuccess(version);
    }

    /*********************故障报修相关********************************/
    @ApiOperation("故障报修: 获取故障内容可选值下拉框")
    @GetMapping("/fault-report/repair-apperance")
    public Response<List<KvObject>> getRepairApperance() {
        List<KvObject> options = Lists.newArrayList();
        List<SobotTicketTypeFiledOption> tmpResult = sobotService.getRepirApperanceOptions();
        if (!CollectionUtils.isEmpty(tmpResult)) {
            options.addAll(tmpResult.stream().map(i -> {
                KvObject data = new KvObject();
                data.setKey(i.getDataName());
                data.setValue(i.getDataValue());
                return data;
            }).collect(Collectors.toList()));
        }
        return returnSuccess(options);
    }

    @ApiOperation("故障报修: 根据家庭获取暖通设备名称下拉框(安卓)")
    @GetMapping("/fault-report/device-name")
    public Response<Set<KvObject>> getFamilyDeviceName(@RequestParam("familyId") String familyId) {
        Set<KvObject> options = Sets.newHashSet();
        KvObject kvObject = new KvObject();
        kvObject.setValue("手动输入");
        kvObject.setKey("手动输入");
        options.add(kvObject);
        return returnSuccess(options);
    }

    @ApiOperation("故障报修: 根据家庭获取暖通设备名称下拉框(IOS)")
    @GetMapping("/fault-report/device-name/{familyId}")
    public Response<Set<KvObject>> getFamilyDeviceName2(@PathVariable("familyId") String familyId) {
        return getFamilyDeviceName(familyId);
    }

    @ApiOperation(value = "故障报修: 故障报修详情查询(安卓)")
    @GetMapping(value = "/fault-report/detail")
    public Response<AppRepairDetailDTO> getRepairDetail(@RequestParam("repairId") String repairId) {
        AppRepairDetailDTO data = homeAutoFaultReportService.getRepairDetail(repairId);
        return returnSuccess(data);
    }

    @ApiOperation(value = "故障报修: 故障报修详情查询(IOS)")
    @GetMapping(value = "/fault-report/detail/{repairId}")
    public Response<AppRepairDetailDTO> getRepairDetail2(@PathVariable("repairId") String repairId) {
        return getRepairDetail(repairId);
    }

    @ApiOperation(value = "故障报修: 故障报修记录查询(安卓)")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header", required = true)
    @PostMapping(value = "/fault-report/list")
    public Response<List<AppRepairDetailDTO>> listRepairs(@RequestParam String familyId) {
        return returnSuccess(homeAutoFaultReportService.listRepairs(familyId));
    }

    @ApiOperation(value = "故障报修: 故障报修记录查询(IOS)")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header", required = true)
    @PostMapping(value = "/fault-report/list/{familyId}")
    public Response<List<AppRepairDetailDTO>> listRepairs2(@PathVariable String familyId) {
        return listRepairs(familyId);
    }

    @ApiOperation(value = "故障报修: 创建报修", notes = "创建报修", consumes = "application/json")
    @PostMapping(value = "/fault-report/add")
    public Response createRepair(@RequestBody RepairAddReqDTO requestBody) {

        homeAutoFaultReportService.createRepair(requestBody, TokenContext.getToken().getUserId());
        return returnSuccess();
    }

    @ApiOperation(value = "故障报修: 修改状态为已完成(安卓)", notes = "修改状态为已完成", consumes = "application/json")
    @PostMapping(value = "/fault-report/status/completed")
    public Response completed(@RequestParam("repairId") String repairId) {

        homeAutoFaultReportService.completed(repairId, TokenContext.getToken().getUserId());
        return returnSuccess();
    }

    @ApiOperation(value = "故障报修: 修改状态为已完成(IOS)", notes = "修改状态为已完成", consumes = "application/json")
    @PostMapping(value = "/fault-report/status/completed/{repairId}")
    public Response completed2(@PathVariable("repairId") String repairId) {

        return completed(repairId);
    }



}
