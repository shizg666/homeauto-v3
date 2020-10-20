package com.landleaf.homeauto.center.device.controller.app.smart;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.landleaf.homeauto.center.device.enums.SceneEnum;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonSceneDO;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyTerminalDO;
import com.landleaf.homeauto.center.device.model.dto.FamilySceneCommonDTO;
import com.landleaf.homeauto.center.device.model.dto.SceneUpdateDTO;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilySceneBO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneVO;
import com.landleaf.homeauto.center.device.model.vo.scene.family.PicVO;
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
import org.springframework.transaction.annotation.Transactional;
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
}
