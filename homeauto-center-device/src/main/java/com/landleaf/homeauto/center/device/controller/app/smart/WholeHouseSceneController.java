package com.landleaf.homeauto.center.device.controller.app.smart;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.landleaf.homeauto.center.device.enums.SceneEnum;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyTerminalDO;
import com.landleaf.homeauto.center.device.model.dto.SceneUpdateDTO;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyDeviceVO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneVO;
import com.landleaf.homeauto.center.device.service.bridge.IAppService;
import com.landleaf.homeauto.center.device.service.mybatis.*;
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
@RequestMapping("/app/smart/scene/whole-house")
@Api(value = "全屋场景控制器", tags = "户式化APP全屋场景接口")
public class WholeHouseSceneController extends BaseController {

    @Autowired
    private IHomeAutoFamilyService familyService;

    @Autowired
    private IFamilySceneService familySceneService;

    @Autowired
    private IFamilyTerminalService familyTerminalService;

    @Autowired
    private IAppService appService;

    /**
     * 查看全屋场景列表
     *
     * @param familyId 家庭ID
     * @return 全屋场景列表
     */
    @GetMapping("/whole-house/list")
    @ApiOperation("查看家庭全屋场景列表(建议用这个接口)")
    public Response<List<FamilySceneVO>> listWholeHouseScene(@RequestParam String familyId) {
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

}
