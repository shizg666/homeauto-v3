package com.landleaf.homeauto.center.device.controller.app.smart;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.landleaf.homeauto.center.device.enums.FamilyReviewStatusEnum;
import com.landleaf.homeauto.center.device.enums.SceneEnum;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneDO;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneHvacConfig;
import com.landleaf.homeauto.center.device.model.dto.SceneUpdateDTO;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyDeviceVO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneHvacConfigService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/app/smart/scene/whole-house")
@Api(value = "全屋场景控制器", tags = "户式化APP全屋场景接口")
public class SceneWholeHouseController extends BaseController {

    @Autowired
    private IFamilySceneService familySceneService;

    @Autowired
    private IHomeAutoFamilyService familyService;

    @Autowired
    private IFamilySceneHvacConfigService familySceneHvacConfigService;

    @Autowired
    private IFamilyDeviceService familyDeviceService;

    /**
     * 查看全屋场景列表
     *
     * @param familyId 家庭ID
     * @return 全屋场景列表
     */
    @GetMapping("/list")
    @ApiOperation("查看家庭全屋场景列表")
    public Response<List<FamilySceneVO>> listWholeHouseScene(@RequestParam String familyId) {
        log.info("检查家庭的审核状态, 家庭ID: {}", familyId);
        FamilyReviewStatusEnum familyReviewStatusEnum = familyService.getFamilyReviewStatus(familyId);
        if (Objects.equals(familyReviewStatusEnum, FamilyReviewStatusEnum.AUTHORIZATION)) {
            throw new BusinessException(90001, "当前家庭授权状态更改中");
        }

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
    @GetMapping("/detail")
    @ApiOperation("查看全屋场景配置")
    public Response<List<FamilyDeviceVO>> detailWhouseHouseScene(@RequestParam String sceneId) {
        // 1. 查询联动智能家居设备
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

        // 2. 查询联动暖通设备
        List<FamilySceneHvacConfig> familySceneHvacConfigList = familySceneHvacConfigService.listBySceneId(sceneId);
        for (FamilySceneHvacConfig familySceneHvacConfig : familySceneHvacConfigList) {
            String familyId = familySceneHvacConfig.getFamilyId();
            String deviceSn = familySceneHvacConfig.getDeviceSn();
            FamilyDeviceBO familyDeviceBO = familyDeviceService.listFamilyDeviceBySn(familyId, deviceSn);
            FamilyDeviceVO familyDeviceVO = new FamilyDeviceVO();
            familyDeviceVO.setDeviceId(familyDeviceBO.getDeviceId());
            familyDeviceVO.setDeviceName(familyDeviceBO.getDeviceName());
            familyDeviceVO.setDeviceIcon(familyDeviceBO.getProductIcon());
            familyDeviceVO.setPosition(familyDeviceBO.getDevicePosition());
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
    public Response<Boolean> edit(@RequestBody SceneUpdateDTO sceneUpdateDTO) {
        UpdateWrapper<FamilySceneDO> familySceneUpdateWrapper = new UpdateWrapper<>();
        familySceneUpdateWrapper.set("icon", sceneUpdateDTO.getSceneIcon());
        familySceneUpdateWrapper.set("mode", sceneUpdateDTO.getSceneIconType());
        familySceneUpdateWrapper.eq("id", sceneUpdateDTO.getSceneId());
        familySceneService.update(familySceneUpdateWrapper);
        return returnSuccess(true);
    }

}
