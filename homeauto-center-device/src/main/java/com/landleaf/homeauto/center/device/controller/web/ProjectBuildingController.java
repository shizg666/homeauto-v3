package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.annotation.LogAnnotation;
import com.landleaf.homeauto.center.device.service.mybatis.IProjectBuildingService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.realestate.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.landleaf.homeauto.common.web.BaseController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 楼栋表 前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@RestController
@RequestMapping("/web/project-building")
@Api(value = "/web/project-building/", tags = {"楼栋配置接口"})
public class ProjectBuildingController extends BaseController {

    @Autowired
    private IProjectBuildingService iProjectBuildingService;


    @ApiOperation(value = "新增楼栋", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("add")
    @LogAnnotation(name ="新增楼栋")
    public Response add(@RequestBody @Valid ProjectBuildingDTO request){
        iProjectBuildingService.addConfig(request);
        return returnSuccess();
    }

    @ApiOperation(value = "修改楼栋（修改id必传）", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("update")
    @LogAnnotation(name ="修改楼栋")
    public Response update(@RequestBody @Valid ProjectBuildingDTO request){
        iProjectBuildingService.updateConfig(request);
        return returnSuccess();
    }

    @ApiOperation(value = "删除楼栋", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("delete")
    @LogAnnotation(name ="删除楼栋")
    public Response delete(@RequestBody ProjectConfigDeleteDTO request){
        iProjectBuildingService.delete(request);
        return returnSuccess();
    }


    @ApiOperation(value = "根据工程id获取楼栋列表", notes = "根据工程id获取楼栋列表")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("list/{id}")
    public Response<List<ProjectBuildingVO>> getListByProjectId(@PathVariable("id") String id){
        List<ProjectBuildingVO> result = iProjectBuildingService.getListByProjectId(id);
        return returnSuccess(result);
    }
}
