package com.landleaf.homeauto.center.device.controller.app.smart;

import com.landleaf.homeauto.center.device.model.dto.FamilySceneCommonDTO;
import com.landleaf.homeauto.center.device.model.dto.TimingSceneDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneDetailVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneTimingDetailVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneTimingVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneTimingService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/8/15
 */
@RestController
@RequestMapping("app/smart/scene")
@Api(value = "场景控制器", tags = "户式化APP场景接口")
public class SceneController extends BaseController {

    private IFamilySceneService familySceneService;

    private IFamilySceneTimingService familySceneTimingService;

    @GetMapping("uncommon")
    @ApiOperation("获取不常用的场景")
    public Response<List<SceneVO>> getFamilyUncommonScenesAndDevices(@RequestParam String familyId) {
        List<SceneVO> uncommonScenesVOList = familySceneService.getUncommonScenesByFamilyId(familyId);
        return returnSuccess(uncommonScenesVOList);
    }

    @GetMapping("whole_house")
    @ApiOperation("查看家庭全屋场景列表")
    public Response<List<SceneVO>> getFamilyWholeHouseScenes(@RequestParam String familyId) {
        List<SceneVO> wholeHouseSceneList = familySceneService.getWholeHouseScenesByFamilyId(familyId);
        return returnSuccess(wholeHouseSceneList);
    }

    @GetMapping("detail")
    @ApiOperation("查看场景内容")
    public Response<List<SceneDetailVO>> getSceneDetail(@RequestParam String sceneId) {
        List<SceneDetailVO> sceneDetailVOList = familySceneService.getSceneDetailBySceneId(sceneId);
        return returnSuccess(sceneDetailVOList);
    }

    @GetMapping("timing")
    @ApiOperation("查看定时场景列表")
    public Response<List<SceneTimingVO>> getTimingSceneList(@RequestParam String familyId) {
        List<SceneTimingVO> sceneTimingVOList = familySceneService.getTimingScenesByFamilyId(familyId);
        return returnSuccess(sceneTimingVOList);
    }

    @GetMapping("timing/detail")
    @ApiOperation("查看定时场景内容")
    public Response<SceneTimingDetailVO> getTimingSceneDetail(@RequestParam String timingId) {
        SceneTimingDetailVO timingSceneDetailVO = familySceneService.getTimingSceneDetailByTimingId(timingId);
        return returnSuccess(timingSceneDetailVO);
    }

    @PostMapping("common/save")
    @ApiOperation("保存常用场景")
    public Response<?> addFamilySceneCommon(@RequestBody FamilySceneCommonDTO familySceneCommonDTO) {
        familySceneService.insertFamilyCommonScene(familySceneCommonDTO);
        return returnSuccess();
    }

    @PostMapping("timing/save")
    @ApiOperation("添加定时场景")
    public Response<String> addFamilySceneTiming(@RequestBody TimingSceneDTO timingSceneDTO) {
        if (familySceneService.isSceneExists(timingSceneDTO.getSceneId())) {
            String timingId = familySceneTimingService.insertOrUpdateFamilySceneTiming(timingSceneDTO);
            return returnSuccess(timingId);
        }
        throw new BusinessException("场景不存在");
    }

    @PostMapping("timing/delete/{timingId}")
    @ApiOperation("删除定时场景")
    public Response<?> deleteFamilySceneTiming(@PathVariable String timingId) {
        familySceneTimingService.deleteFamilySceneById(timingId);
        return returnSuccess();
    }

    @Autowired
    public void setFamilySceneService(IFamilySceneService familySceneService) {
        this.familySceneService = familySceneService;
    }

    @Autowired
    public void setFamilySceneTimingService(IFamilySceneTimingService familySceneTimingService) {
        this.familySceneTimingService = familySceneTimingService;
    }
}
