package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.annotation.LogAnnotation;
import com.landleaf.homeauto.center.device.model.dto.house.TemplateFloorDTO;
import com.landleaf.homeauto.center.device.model.dto.house.TemplateRoomDTO;
import com.landleaf.homeauto.center.device.model.vo.product.ProductInfoSelectVO;
import com.landleaf.homeauto.center.device.model.vo.project.*;
import com.landleaf.homeauto.center.device.model.vo.scene.house.HouseSceneDTO;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 项目户型表 前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@RestController
@RequestMapping("/web/house-template/scene/")
@Api(value = "/web/house-template/", tags = {"户型场景接口"})
public class HouseTemplateSceneController extends BaseController {
    @Autowired
    private IProjectHouseTemplateService iProjectHouseTemplateService;
    @Autowired
    private IHouseTemplateTerminalService iTemplateTerminalService;
    @Autowired
    private IHouseTemplateFloorService iHouseTemplateFloorService;
    @Autowired
    private IHouseTemplateRoomService iHouseTemplateRoomService;
    @Autowired
    private IHouseTemplateDeviceService iHouseTemplateDeviceService;
    @Autowired
    private IHomeAutoProductService iHomeAutoProductService;

    @Autowired
    private IHouseTemplateSceneService iHouseTemplateSceneService;


    @ApiOperation(value = "新增场景", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("add")
    @LogAnnotation(name ="新增户型场景")
    public Response add(@RequestBody @Valid HouseSceneDTO request){
        iHouseTemplateSceneService.add(request);
        return returnSuccess();
    }


    @ApiOperation(value = "修改户型场景（修改id必传）", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("update")
    @LogAnnotation(name ="修改户型场景")
    public Response update(@RequestBody @Valid HouseSceneDTO request){
        iHouseTemplateSceneService.update(request);
        return returnSuccess();
    }


    @ApiOperation(value = "删除户型场景", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("delete")
    @LogAnnotation(name ="删除户型场景")
    public Response delete(@RequestBody ProjectConfigDeleteDTO request){
        iHouseTemplateSceneService.delete(request);
        return returnSuccess();
    }

}
