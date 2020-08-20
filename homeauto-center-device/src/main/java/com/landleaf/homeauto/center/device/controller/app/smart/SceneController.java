package com.landleaf.homeauto.center.device.controller.app.smart;

import com.landleaf.homeauto.center.device.model.dto.FamilySceneCommonDTO;
import com.landleaf.homeauto.center.device.model.vo.FamilySceneVO;
import com.landleaf.homeauto.center.device.model.vo.SceneDetailVO;
import com.landleaf.homeauto.center.device.model.vo.TimingSceneDetailVO;
import com.landleaf.homeauto.center.device.model.vo.TimingSceneVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.ObjectUtils;
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

    @GetMapping("uncommon")
    @ApiOperation("获取不常用的场景")
    public Response<List<FamilySceneVO>> getFamilyUncommonScenesAndDevices(@RequestParam String familyId) {
        List<FamilySceneVO> uncommonScenesVOList = familySceneService.getUncommonScenesByFamilyId(familyId);
        return returnSuccess(uncommonScenesVOList);
    }

    @GetMapping("whole_house")
    @ApiOperation("获取家庭全屋场景")
    public Response<List<FamilySceneVO>> getFamilyWholeHouseScenes(@RequestParam String familyId) {
        List<FamilySceneVO> wholeHouseSceneList = familySceneService.getWholeHouseScenesByFamilyId(familyId);
        return returnSuccess(wholeHouseSceneList);
    }

    @GetMapping("detail")
    @ApiOperation("获取场景内容")
    public Response<List<SceneDetailVO>> getSceneDetail(@RequestParam String sceneId) {
        List<SceneDetailVO> sceneDetailVOList = familySceneService.getSceneDetailBySceneId(sceneId);
        return returnSuccess(sceneDetailVOList);
    }

    @GetMapping("timing")
    @ApiOperation("查看定时场景")
    public Response<List<TimingSceneVO>> getTimingSceneList(@RequestParam String familyId) {
        List<TimingSceneVO> timingSceneVOList = familySceneService.getTimingScenesByFamilyId(familyId);
        return returnSuccess(timingSceneVOList);
    }

    @GetMapping("timing/detail")
    @ApiOperation("查看定时场景内容")
    public Response<TimingSceneDetailVO> getTimingSceneDetail(@RequestParam String timingId) {
        TimingSceneDetailVO timingSceneDetailVO = familySceneService.getTimingSceneDetailByTimingId(timingId);
        return returnSuccess(timingSceneDetailVO);
    }

    @PostMapping("common/save")
    @ApiOperation("保存常用场景")
    public Response<?> addFamilySceneCommon(@RequestBody FamilySceneCommonDTO familySceneCommonDTO) {
        familySceneService.insertFamilyCommonScene(familySceneCommonDTO);
        return returnSuccess();
    }

    @Autowired
    public void setFamilySceneService(IFamilySceneService familySceneService) {
        this.familySceneService = familySceneService;
    }

}
