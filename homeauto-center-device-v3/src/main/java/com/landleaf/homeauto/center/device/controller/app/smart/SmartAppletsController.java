package com.landleaf.homeauto.center.device.controller.app.smart;

import com.landleaf.homeauto.center.device.model.dto.TimingSceneAppletsDTO;
import com.landleaf.homeauto.center.device.model.smart.vo.AppletsDeviceInfoVO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyAllDeviceVO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyUncommonDeviceVO;
import com.landleaf.homeauto.center.device.model.vo.MyFamilyDetailInfoAppletsVO;
import com.landleaf.homeauto.center.device.model.vo.MyFamilyDetailInfoVO;
import com.landleaf.homeauto.center.device.model.vo.scene.AppletsSceneTimingDetailVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneTimingDetailVO;
import com.landleaf.homeauto.center.device.service.AppletsService;
import com.landleaf.homeauto.center.device.service.IContactScreenService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneTimingService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.landleaf.homeauto.common.web.context.TokenContextUtil.getUserIdForAppRequest;


/**
 *  户式化小程序额外接口处理器
 *  2021/1/12 9:14
 * @author wenyilu
 */
@RestController
@RequestMapping("/app/smart")
@Slf4j
@Api(value = "SmartAppletsController", tags = {"户式化小程序额外接口处理器"})
public class SmartAppletsController extends BaseController {
    @Autowired
    private IContactScreenService contactScreenService;
    @Autowired
    private AppletsService appletsService;
    /**
     * 添加或修改定时场景
     *
     * @param timingSceneDTO 定时场景信息
     * @return 操作结果
     */
    @PostMapping("/applets/scene/timing/save")
    @ApiOperation("场景定时: 添加定时场景")
    public Response<Boolean> save(@RequestBody TimingSceneAppletsDTO timingSceneDTO) {
        appletsService.saveTimingScene(timingSceneDTO);
        // 通知大屏定时配置更新
        try {
            contactScreenService.notifySceneTimingConfigUpdate(BeanUtil.convertString2Long(timingSceneDTO.getFamilyId()), ContactScreenConfigUpdateTypeEnum.SCENE_TIMING);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnSuccess();
    }

    /**
     * 小程序获取设备属性及状态
     *
     * @param deviceId 设备ID
     * @return 小程序获取设备属性及状态
     */
    @GetMapping("/applets/device/status/{familyId}/{deviceId}")
    @ApiOperation(value = "设备: 小程序获取设备属性及状态")
    public Response<AppletsDeviceInfoVO> getDeviceStatus(@PathVariable String familyId, @PathVariable String deviceId) {
        AppletsDeviceInfoVO result = appletsService.getDeviceStatus4AppletsVO(BeanUtil.convertString2Long(familyId), BeanUtil.convertString2Long(deviceId));
        return returnSuccess(result);
    }

    /**
     * 小程序查看定时场景内容
     *
     * @param timingId 定时场景ID
     * @return 定时场景内容
     */
    @GetMapping("/applets/scene/timing/detail")
    @ApiOperation("场景定时: 查看定时场景内容")
    public Response<AppletsSceneTimingDetailVO> getTimingSceneDetail(@RequestParam String timingId) {
        return returnSuccess(appletsService.getTimingSceneDetail4Applets(BeanUtil.convertString2Long(timingId)));
    }

    @GetMapping("/applets/family-manager/my/info/{familyId}")
    @ApiOperation("家庭管理：获取某个家庭详情：楼层、房间、设备、用户信息等简要信息")
    public Response<MyFamilyDetailInfoAppletsVO> getMyFamilyInfo(@PathVariable("familyId") String familyId) {
        return returnSuccess(appletsService.getMyFamilyInfo4Applets(BeanUtil.convertString2Long(familyId),getUserIdForAppRequest()));
    }

    /**
     * 获取家庭所有的设备
     *
     * @param familyId 家庭ID
     * @return 不常用设备列表
     */
    @GetMapping("/applets/device/all")
    @ApiOperation(value = "设备: 获取家庭所有设备")
    public Response<List<FamilyAllDeviceVO>> getAllDevices(@RequestParam("familyId") String familyId) {
        return returnSuccess(appletsService.getAllDevices(BeanUtil.convertString2Long(familyId)));
    }

}
