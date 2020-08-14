package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.annotation.LogAnnotation;
import com.landleaf.homeauto.center.device.service.mybatis.IProjectBuildingUnitService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectBuildingUnitDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectBuildingUnitVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectBuildingVO;
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
 * 楼栋单元表 前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@RestController
@RequestMapping("/web/project-building-unit/")
@Api(value = "/web/project-building-unit/", tags = {"单元配置接口"})
public class ProjectBuildingUnitController extends BaseController {

    @Autowired
    private IProjectBuildingUnitService iProjectBuildingUnitService;


    @ApiOperation(value = "新增单元", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("add")
    @LogAnnotation(name ="新增单元")
    public Response add(@RequestBody  @Valid  ProjectBuildingUnitDTO request){
        iProjectBuildingUnitService.add(request);
        return returnSuccess();
    }

    @ApiOperation(value = "修改单元（修改id必传）", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("update")
    @LogAnnotation(name ="修改单元")
    public Response update(@RequestBody  @Valid ProjectBuildingUnitDTO request){
        iProjectBuildingUnitService.update(request);
        return returnSuccess();
    }

    @ApiOperation(value = "删除单元", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("delete")
    @LogAnnotation(name ="删除单元")
    public Response delete(@RequestBody @Valid ProjectConfigDeleteDTO request){
        iProjectBuildingUnitService.delete(request);
        return returnSuccess();
    }

    @ApiOperation(value = "根据楼栋id获取单元列表", notes = "根据工程id获取楼栋列表")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("list/{id}")
    public Response<List<ProjectBuildingUnitVO>> getListByProjectId(@PathVariable("id") String id){
        List<ProjectBuildingUnitVO> result = iProjectBuildingUnitService.getListByProjectId(id);
        return returnSuccess(result);
    }

}
