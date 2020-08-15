package com.landleaf.homeauto.center.device.controller.app.smart;

import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import com.landleaf.homeauto.model.vo.device.FamilySceneVO;
import com.landleaf.homeauto.model.vo.device.SceneDetailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/8/15
 */
@RestController
@RequestMapping("scene")
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

    @Autowired
    public void setFamilySceneService(IFamilySceneService familySceneService) {
        this.familySceneService = familySceneService;
    }

}
